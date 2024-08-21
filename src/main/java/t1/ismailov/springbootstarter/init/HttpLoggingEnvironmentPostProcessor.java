package t1.ismailov.springbootstarter.init;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import t1.ismailov.springbootstarter.config.HttpLoggingAutoConfiguration;
import t1.ismailov.springbootstarter.exeption.HttpLoggingStartupException;

import java.util.function.Function;

@Slf4j
@AutoConfiguration
@AutoConfigureBefore(HttpLoggingAutoConfiguration.class)
public class HttpLoggingEnvironmentPostProcessor implements EnvironmentPostProcessor {

    public static final String PROPERTY_HTTP_LOGGING_ENABLED = "http.logging.enabled";
    public static final String PROPERTY_HTTP_LOGGING_FORMAT = "http.logging.format";
    public static final String PROPERTY_HTTP_LOGGING_LEVEL =
            "logging.level.t1.ismailov.springbootstarter.config.HttpLoggingInterceptor";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        log.info("Running HttpLoggingEnvironmentPostProcessor");

        validateProperty(environment, PROPERTY_HTTP_LOGGING_ENABLED, this::isBoolValue, "true or false");
        validateProperty(environment, PROPERTY_HTTP_LOGGING_FORMAT, this::validFormat, "json or text");
        validateProperty(environment, PROPERTY_HTTP_LOGGING_LEVEL, this::shouldLogging, "debug, info, error, warn");

        log.debug("HttpLoggingEnvironmentPostProcessor completed successfully");
    }

    private void validateProperty(ConfigurableEnvironment environment,
                                  String propertyName,
                                  Function<String, Boolean> validationFunction,
                                  String validValues) {
        String propertyValue = environment.getProperty(propertyName);

        log.debug("Checking property '{}': {}", propertyName, propertyValue);

        if (propertyValue == null || !validationFunction.apply(propertyValue)) {
            log.error("Invalid value for '{}': {}", propertyName, propertyValue);
            throw new HttpLoggingStartupException("Error checking property '" + propertyName +
                    "' in configuration file. Valid values: " + validValues + "!");
        }
    }

    private boolean isBoolValue(String enablePropertyValue) {
        return switch (enablePropertyValue.toLowerCase()) {
            case "true", "false" -> true;
            default -> false;
        };
    }

    private boolean validFormat(String formatPropertyValue) {
        return switch (formatPropertyValue.toLowerCase()) {
            case "json", "text" -> true;
            default -> false;
        };
    }

    private boolean shouldLogging(String levelPropertyValue) {
        return switch (levelPropertyValue.toUpperCase()) {
            case "DEBUG", "INFO", "ERROR", "WARN" -> true;
            default -> false;
        };
    }
}