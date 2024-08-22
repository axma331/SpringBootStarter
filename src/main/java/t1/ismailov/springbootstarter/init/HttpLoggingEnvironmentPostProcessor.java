package t1.ismailov.springbootstarter.init;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import t1.ismailov.springbootstarter.exeption.HttpLoggingStartupException;

import java.util.function.Function;

/**
 * {@code HttpLoggingEnvironmentPostProcessor} is responsible for processing and validating
 * environment properties related to HTTP logging before the application's auto-configuration.
 * This class ensures that the required properties for HTTP logging are correctly set and valid.
 */
public class HttpLoggingEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private static final Logger log = LoggerFactory.getLogger(HttpLoggingEnvironmentPostProcessor.class);

    public static final String PROPERTY_HTTP_LOGGING_ENABLED = "http.logging.enabled";
    public static final String PROPERTY_HTTP_LOGGING_FORMAT = "http.logging.format";
    public static final String PROPERTY_HTTP_LOGGING_LEVEL =
            "logging.level.t1.ismailov.springbootstarter.config.HttpLoggingInterceptor";

    /**
     * Processes the environment to validate the properties necessary for HTTP logging.
     * If any property is invalid or missing, an exception is thrown.
     *
     * @param environment the environment to process
     * @param application the current Spring application
     */
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        log.info("Running HttpLoggingEnvironmentPostProcessor");

        validateProperty(environment, PROPERTY_HTTP_LOGGING_ENABLED, this::isBoolValue, "true or false");
        validateProperty(environment, PROPERTY_HTTP_LOGGING_FORMAT, this::validFormat, "json or text");
        validateProperty(environment, PROPERTY_HTTP_LOGGING_LEVEL, this::shouldLogging, "debug, info, error, warn");

        log.debug("HttpLoggingEnvironmentPostProcessor completed successfully");
    }

    /**
     * Validates a specific environment property based on the provided validation function.
     *
     * @param environment        the environment to process
     * @param propertyName       the name of the property to validate
     * @param validationFunction the function to validate the property value
     * @param validValues        a description of valid values for error reporting
     * @throws HttpLoggingStartupException if the property is invalid
     */
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
