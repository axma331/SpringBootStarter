package t1.ismailov.springbootstarter.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@code HttpLoggingProperties} is a configuration properties class that binds to properties
 * prefixed with "http.logging". It holds configuration settings related to HTTP logging,
 * such as whether logging is enabled and the format of the log output.
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "http.logging")
public class HttpLoggingProperties {

    /**
     * Indicates whether HTTP logging is enabled.
     * This property corresponds to "http.logging.enabled" in the configuration.
     */
    private Boolean enabled;
    /**
     * The format of the HTTP log output (e.g., "json" or "text").
     * This property corresponds to "http.logging.format" in the configuration.
     */
    private String format;

}
