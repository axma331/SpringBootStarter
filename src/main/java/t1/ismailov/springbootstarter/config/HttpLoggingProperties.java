package t1.ismailov.springbootstarter.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "http.logging")
public class HttpLoggingProperties {

    private Boolean enabled;
    private String format;

}
