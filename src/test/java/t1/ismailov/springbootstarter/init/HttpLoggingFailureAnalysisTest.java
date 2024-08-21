package t1.ismailov.springbootstarter.init;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.diagnostics.FailureAnalysis;
import t1.ismailov.springbootstarter.exeption.HttpLoggingStartupException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class HttpLoggingFailureAnalysisTest {

    private HttpLoggingFailureAnalysis failureAnalyzer;

    @Mock
    private HttpLoggingStartupException mockException;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        failureAnalyzer = new HttpLoggingFailureAnalysis();
    }

    @Test
    void testAnalyzeWithHttpLoggingStartupException() {
        String errorMessage = "Invalid value for 'http.logging.enabled'";

        when(mockException.getMessage()).thenReturn(errorMessage);

        FailureAnalysis analysis = failureAnalyzer.analyze(mockException, mockException);

        assertNotNull(analysis, "FailureAnalysis should not be null");
        assertEquals(errorMessage, analysis.getDescription(),
                "FailureAnalysis description should match exception message");
        assertEquals("Provide valid values for the property in the configuration file.", analysis.getAction(),
                "FailureAnalysis action should be as expected");
        assertEquals(mockException, analysis.getCause(),
                "FailureAnalysis cause should match the original exception");
    }

    @Test
    void testAnalyzeWithDifferentRootCause() {
        Throwable rootCause = new RuntimeException("Different root cause");
        String errorMessage = "Invalid value for 'http.logging.format'";
        when(mockException.getMessage()).thenReturn(errorMessage);

        FailureAnalysis analysis = failureAnalyzer.analyze(rootCause, mockException);

        assertNotNull(analysis, "FailureAnalysis should not be null");
        assertEquals(errorMessage, analysis.getDescription(),
                "FailureAnalysis description should match exception message");
        assertEquals("Provide valid values for the property in the configuration file.", analysis.getAction(),
                "FailureAnalysis action should be as expected");
        assertEquals(mockException, analysis.getCause(),
                "FailureAnalysis cause should match the original exception");
    }
}
