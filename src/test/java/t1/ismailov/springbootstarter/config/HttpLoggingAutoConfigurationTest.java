package t1.ismailov.springbootstarter.config;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.assertj.core.api.Assertions.assertThat;

class HttpLoggingAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(HttpLoggingAutoConfiguration.class));

    @Test
    void loggingInterceptorShouldBeCreatedWhenEnabled() {
        contextRunner
                .withPropertyValues("http.logging.enabled=true")
                .run(context -> {
                    assertThat(context).hasSingleBean(HttpLoggingInterceptor.class);
                    assertThat(context).hasSingleBean(WebMvcConfigurer.class);
                });
    }

    @Test
    void loggingInterceptorShouldNotBeCreatedWhenDisabled() {
        contextRunner
                .withPropertyValues("http.logging.enabled=false")
                .run(context -> {
                    assertThat(context).doesNotHaveBean(HttpLoggingInterceptor.class);
                    assertThat(context).doesNotHaveBean(WebMvcConfigurer.class);
                });
    }

    @Test
    void loggingInterceptorShouldNotBeCreatedWhenPropertyIsMissing() {
        contextRunner.run(context -> {
            assertThat(context).doesNotHaveBean(HttpLoggingInterceptor.class);
            assertThat(context).doesNotHaveBean(WebMvcConfigurer.class);
        });
    }

    @Test
    void webMvcConfigurerShouldBeCreatedWhenLoggingInterceptorExists() {
        contextRunner
                .withPropertyValues("http.logging.enabled=true")
                .run(context -> {
                    assertThat(context).hasSingleBean(WebMvcConfigurer.class);
                    WebMvcConfigurer configurer = context.getBean(WebMvcConfigurer.class);
                    assertThat(configurer).isNotNull();
                });
    }

    @Test
    void webMvcConfigurerShouldNotBeCreatedWhenLoggingInterceptorIsMissing() {
        contextRunner
                .withUserConfiguration(NoInterceptorConfiguration.class)
                .run(context -> {
                    assertThat(context).doesNotHaveBean(WebMvcConfigurer.class);
                });
    }

    @Configuration(proxyBeanMethods = false)
    private static class NoInterceptorConfiguration {
        @Bean
        public HttpLoggingProperties httpLoggingProperties() {
            return Mockito.mock(HttpLoggingProperties.class);
        }
    }
}
