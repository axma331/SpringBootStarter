package t1.ismailov.springbootstarter.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * {@code HttpLoggingAutoConfiguration} is an auto-configuration class that sets up the
 * HTTP logging infrastructure if the appropriate conditions are met. It creates and configures
 * the {@link HttpLoggingInterceptor} bean and registers it with Spring MVC if HTTP logging
 * is enabled in the application's configuration.
 * <p>
 * To enable HTTP logging, the property {@code http.logging.enabled} must be set to {@code true}.
 * The logging format can be specified using the {@code http.logging.format} property, which
 * accepts values such as "json" or "text".
 * <p>
 * The logging level for the interceptor can be configured using the property:
 * {@code logging.level.t1.ismailov.springbootstarter.config.HttpLoggingInterceptor}.
 * This property should be set to the desired logging level, such as "DEBUG", "INFO", etc.
 * <p>
 * Additionally, to control the logging level for the entire package containing the HTTP logging
 * functionality, you can use the property:
 * {@code logging.level.t1.ismailov.springbootstarter}. This allows you to adjust the verbosity
 * of logs across all classes in this package.
 */
@Slf4j
@AutoConfiguration
@EnableConfigurationProperties(HttpLoggingProperties.class)
@ConditionalOnProperty(prefix = "http.logging", value = "enabled", havingValue = "true")
public class HttpLoggingAutoConfiguration {

    /**
     * Creates a {@link HttpLoggingInterceptor} bean if one is not already present in the context.
     * This interceptor will be used to log HTTP requests and responses.
     *
     * @param loggingProperties the properties for configuring HTTP logging
     * @return the configured {@link HttpLoggingInterceptor} bean
     */
    @Bean
    @ConditionalOnExpression("${http.logging.enabled:false}")
    public HttpLoggingInterceptor loggingInterceptor(HttpLoggingProperties loggingProperties) {
        log.info("Creating HttpLoggingInterceptor bean");
        return new HttpLoggingInterceptor(loggingProperties);
    }

    /**
     * Configures a {@link WebMvcConfigurer} that registers the {@link HttpLoggingInterceptor}
     * with Spring MVC. This configuration is applied only if the {@code loggingInterceptor} bean
     * is present in the application context.
     *
     * @return a configured {@link WebMvcConfigurer} instance that adds the interceptor
     */
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
