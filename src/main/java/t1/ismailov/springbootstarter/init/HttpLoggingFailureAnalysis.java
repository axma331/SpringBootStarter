package t1.ismailov.springbootstarter.init;


import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;
import t1.ismailov.springbootstarter.exeption.HttpLoggingStartupException;

public class HttpLoggingFailureAnalysis extends AbstractFailureAnalyzer<HttpLoggingStartupException> {

    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, HttpLoggingStartupException cause) {

        return new FailureAnalysis(cause.getMessage(),
                "Provide valid values for the property in the configuration file.", cause);
    }

}
