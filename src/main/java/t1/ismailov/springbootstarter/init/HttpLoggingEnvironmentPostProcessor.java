package t1.ismailov.springbootstarter.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import t1.ismailov.springbootstarter.exeption.HttpLoggingStartupException;

@Slf4j
public class HttpLoggingEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private static final String PROPERTY_HTTP_LOGGING_ENABLED = "http.logging.enabled";
    private static final String PROPERTY_HTTP_LOGGING_FORMAT = "http.logging.format";
    private static final String PROPERTY_HTTP_LOGGING_LEVEL =
            "logging.level.t1.ismailov.springbootstarter.config.HttpLoggingInterceptor";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        log.info("Running HttpLoggingEnvironmentPostProcessor");

        String enabled = environment.getProperty(PROPERTY_HTTP_LOGGING_ENABLED);
        String format = environment.getProperty(PROPERTY_HTTP_LOGGING_FORMAT);
        String level = environment.getProperty(PROPERTY_HTTP_LOGGING_LEVEL);

        log.debug("HttpLogging configuration - enabled: {}, format: {}, level: {}", enabled, format, level);

        if (!isBoolValue(enabled)) {
            log.error("Invalid value for '{}': {}", PROPERTY_HTTP_LOGGING_ENABLED, enabled);
            throw new HttpLoggingStartupException("Error checking property '" + PROPERTY_HTTP_LOGGING_ENABLED + "' " +
                    "in configuration file. Valid values: true or false!{}");
        }

        if (!validFormat(format)) {
            log.error("Invalid value for '{}': {}", PROPERTY_HTTP_LOGGING_FORMAT, format);
            throw new HttpLoggingStartupException("Error checking property '" + PROPERTY_HTTP_LOGGING_FORMAT + "' " +
                    "in configuration file. Valid values: json or text!");
        }

        if (!shouldLogging(level)) {
            log.error("Invalid value for '{}': {}", PROPERTY_HTTP_LOGGING_LEVEL, level);
            throw new HttpLoggingStartupException("Error checking property '" + PROPERTY_HTTP_LOGGING_LEVEL + "' " +
                    "in configuration file. Valid values: debug, info, error, warning!");
        }

        log.debug("HttpLoggingEnvironmentPostProcessor completed successfully");
    }


    private boolean isBoolValue(String enablePropertyValue) {
        return enablePropertyValue != null && switch (enablePropertyValue.toLowerCase()) {
            case "true", "false" -> true;
            default -> false;
        };
    }

    private boolean validFormat(String formatPropertyValue) {
        return formatPropertyValue != null && switch (formatPropertyValue.toLowerCase()) {
            case "json", "text" -> true;
            default -> false;
        };
    }

    private boolean shouldLogging(String levelPropertyValue) {
        return levelPropertyValue != null && switch (levelPropertyValue.toUpperCase()) {
            case "DEBUG", "INFO", "ERROR", "WARNING" -> true;
            default -> false;
        };
    }

}
