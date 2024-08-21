package t1.ismailov.springbootstarter.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@AutoConfiguration
@EnableConfigurationProperties(HttpLoggingProperties.class)
@ConditionalOnProperty(prefix = "http.logging", value = "enabled", havingValue = "true")
public class HttpLoggingAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public HttpLoggingInterceptor loggingInterceptor(HttpLoggingProperties loggingProperties) {
        log.info("Creating HttpLoggingInterceptor bean");
        return new HttpLoggingInterceptor(loggingProperties);
    }

    @Bean
    @ConditionalOnBean(name = "loggingInterceptor")
    public WebMvcConfigurer webMvcConfigurer(HttpLoggingInterceptor interceptor) {
        log.info("Configuring WebMvcConfigurer to add HttpLoggingInterceptor");
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                log.debug("Adding HttpLoggingInterceptor to InterceptorRegistry");
                registry.addInterceptor(interceptor);
            }
        };
    }

}
