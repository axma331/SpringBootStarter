package t1.ismailov.springbootstarter.init;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;
import t1.ismailov.springbootstarter.config.HttpLoggingAutoConfiguration;
import t1.ismailov.springbootstarter.exeption.HttpLoggingStartupException;

/**
 * {@code HttpLoggingFailureAnalysis} is a custom failure analyzer that provides detailed
 * analysis for {@link HttpLoggingStartupException}. It helps in diagnosing the root cause
 * of failures related to HTTP logging configuration.
 */
@Slf4j
@AutoConfiguration
@AutoConfigureBefore(HttpLoggingAutoConfiguration.class)
public class HttpLoggingFailureAnalysis extends AbstractFailureAnalyzer<HttpLoggingStartupException> {

    /**
     * Analyzes the cause of a {@link HttpLoggingStartupException} and provides a detailed
     * {@link FailureAnalysis} with a description of the error and a suggested action.
     *
     * @param rootFailure the root cause of the failure
     * @param cause       the specific {@link HttpLoggingStartupException} that was thrown
     * @return a {@link FailureAnalysis} object containing the analysis details
     */
    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, HttpLoggingStartupException cause) {
        log.error("Analyzing failure in HttpLogging configuration.");
        return new FailureAnalysis(
                cause.getMessage(),
                "Provide valid values for the property in the configuration file.",
                cause);
    }

}
