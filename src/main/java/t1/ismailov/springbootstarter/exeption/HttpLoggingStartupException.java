package t1.ismailov.springbootstarter.exeption;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class HttpLoggingStartupException extends RuntimeException {

    public HttpLoggingStartupException(String message) {
        super(message);
    }

}
