package t1.ismailov.springbootstarter.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

class HttpLoggingPropertiesTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(HttpLoggingAutoConfiguration.class))
            .withPropertyValues("");

    @Test
    void testHttpLoggingPropertiesDefaultValues() {
        contextRunner
                .withPropertyValues("http.logging.enabled=true")
                .run(context -> {
                    HttpLoggingProperties properties = context.getBean(HttpLoggingProperties.class);

                    assertThat(properties).isNotNull();
                    assertThat(properties.getEnabled()).isTrue();
                    assertThat(properties.getFormat()).isNull(); // Нет значения по умолчанию
                });
    }

    @Test
    void testHttpLoggingPropertiesBinding() {
        contextRunner
                .withPropertyValues(
                        "http.logging.enabled=true",
                        "http.logging.format=JSON")
                .run(context -> {
                    HttpLoggingProperties properties = context.getBean(HttpLoggingProperties.class);

                    assertThat(properties).isNotNull();
                    assertThat(properties.getEnabled()).isTrue();
                    assertThat(properties.getFormat()).isEqualTo("JSON");
                });
    }

    @Test
    void testHttpLoggingPropertiesNotSet() {
        new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(HttpLoggingAutoConfiguration.class))
                .run(context -> {
                    assertThat(context).doesNotHaveBean(HttpLoggingProperties.class);
                });
    }
}
