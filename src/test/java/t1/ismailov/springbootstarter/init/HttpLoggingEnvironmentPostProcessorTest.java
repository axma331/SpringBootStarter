package t1.ismailov.springbootstarter.init;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.ConfigurableEnvironment;
import t1.ismailov.springbootstarter.exeption.HttpLoggingStartupException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static t1.ismailov.springbootstarter.init.HttpLoggingEnvironmentPostProcessor.PROPERTY_HTTP_LOGGING_ENABLED;
import static t1.ismailov.springbootstarter.init.HttpLoggingEnvironmentPostProcessor.PROPERTY_HTTP_LOGGING_FORMAT;
import static t1.ismailov.springbootstarter.init.HttpLoggingEnvironmentPostProcessor.PROPERTY_HTTP_LOGGING_LEVEL;

class HttpLoggingEnvironmentPostProcessorTest {

    private HttpLoggingEnvironmentPostProcessor postProcessor;

    @Mock
    private ConfigurableEnvironment environment;

    @Mock
    private SpringApplication application;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        postProcessor = new HttpLoggingEnvironmentPostProcessor();
    }

    @Test
    void testPostProcessEnvironmentWithValidProperties() {

        when(environment.getProperty(PROPERTY_HTTP_LOGGING_ENABLED)).thenReturn("true");
        when(environment.getProperty(PROPERTY_HTTP_LOGGING_FORMAT)).thenReturn("json");
        when(environment.getProperty(PROPERTY_HTTP_LOGGING_LEVEL)).thenReturn("DEBUG");

        postProcessor.postProcessEnvironment(environment, application);

        verify(environment).getProperty(PROPERTY_HTTP_LOGGING_ENABLED);
        verify(environment).getProperty(PROPERTY_HTTP_LOGGING_FORMAT);
        verify(environment).getProperty(PROPERTY_HTTP_LOGGING_LEVEL);
    }

    @Test
    void testPostProcessEnvironmentWithInvalidEnabledValue() {

        when(environment.getProperty(PROPERTY_HTTP_LOGGING_ENABLED)).thenReturn("other");

        assertThrows(HttpLoggingStartupException.class, () -> {
            postProcessor.postProcessEnvironment(environment, application);
        });

        verify(environment).getProperty(PROPERTY_HTTP_LOGGING_ENABLED);
    }

    @Test
    void testPostProcessEnvironmentWithInvalidFormatValue() {

        when(environment.getProperty(PROPERTY_HTTP_LOGGING_ENABLED)).thenReturn("true");
        when(environment.getProperty(PROPERTY_HTTP_LOGGING_FORMAT)).thenReturn("otherFormat");

        assertThrows(HttpLoggingStartupException.class, () -> {
            postProcessor.postProcessEnvironment(environment, application);
        });

        verify(environment).getProperty(PROPERTY_HTTP_LOGGING_ENABLED);
        verify(environment).getProperty(PROPERTY_HTTP_LOGGING_FORMAT);
    }

    @Test
    void testPostProcessEnvironmentWithInvalidLoggingLevel() {

        when(environment.getProperty(PROPERTY_HTTP_LOGGING_ENABLED)).thenReturn("true");
        when(environment.getProperty(PROPERTY_HTTP_LOGGING_FORMAT)).thenReturn("json");
        when(environment.getProperty(PROPERTY_HTTP_LOGGING_LEVEL)).thenReturn("TRACE");

        assertThrows(HttpLoggingStartupException.class, () -> {
            postProcessor.postProcessEnvironment(environment, application);
        });

        verify(environment).getProperty(PROPERTY_HTTP_LOGGING_ENABLED);
        verify(environment).getProperty(PROPERTY_HTTP_LOGGING_FORMAT);
        verify(environment).getProperty(PROPERTY_HTTP_LOGGING_LEVEL);
    }

    @Test
    void testPostProcessEnvironmentWithMissingProperties() {

        when(environment.getProperty(PROPERTY_HTTP_LOGGING_ENABLED)).thenReturn(null);
        when(environment.getProperty(PROPERTY_HTTP_LOGGING_FORMAT)).thenReturn(null);
        when(environment.getProperty(PROPERTY_HTTP_LOGGING_LEVEL)).thenReturn(null);

        assertThrows(HttpLoggingStartupException.class, () -> {
            postProcessor.postProcessEnvironment(environment, application);
        });
    }
}
