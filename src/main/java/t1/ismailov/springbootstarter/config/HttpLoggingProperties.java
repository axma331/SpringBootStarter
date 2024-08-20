package t1.ismailov.springbootstarter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "http.logging")
public class HttpLoggingProperties {

    private Boolean enabled;
    private String format;

}
