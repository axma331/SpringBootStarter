package t1.ismailov.springbootstarter.exeption;

/**
 * This exception is thrown when there is an error during the startup of the HTTP logging component.
 * <p>
 * This can occur due to configuration issues or other problems that prevent the HTTP logging
 * component from initializing correctly.
 * </p>
 *
 * <p>The class extends {@link RuntimeException}, making it an unchecked exception.</p>
 */
public class HttpLoggingStartupException extends RuntimeException {

    public HttpLoggingStartupException(String message) {
        super(message);
    }

}
