package t1.ismailov.springbootstarter.init;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;
import t1.ismailov.springbootstarter.config.HttpLoggingAutoConfiguration;
import t1.ismailov.springbootstarter.exeption.HttpLoggingStartupException;

@Slf4j
@AutoConfiguration
@AutoConfigureBefore(HttpLoggingAutoConfiguration.class)
public class HttpLoggingFailureAnalysis extends AbstractFailureAnalyzer<HttpLoggingStartupException> {

    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, HttpLoggingStartupException cause) {
        log.error("Analyzing failure in HttpLogging configuration.");
        return new FailureAnalysis(
                cause.getMessage(),
                "Provide valid values for the property in the configuration file.",
                cause);
    }

}
