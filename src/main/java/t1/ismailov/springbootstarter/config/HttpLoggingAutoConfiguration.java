package t1.ismailov.springbootstarter.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@AutoConfiguration
//@ConditionalOnWebApplication //todo удалить если нет надобности
@EnableConfigurationProperties(HttpLoggingProperties.class)
@ConditionalOnProperty(prefix = "http.logging", value = "enabled", havingValue = "true")
public class HttpLoggingAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public HttpLoggingInterceptor interceptor() {
        return new HttpLoggingInterceptor();
    }

    @Bean
    @ConditionalOnBean(name = "interceptor")
    public WebMvcConfigurer webMvcConfigurer(HttpLoggingInterceptor interceptor) {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(interceptor);
            }
        };
    }

}
