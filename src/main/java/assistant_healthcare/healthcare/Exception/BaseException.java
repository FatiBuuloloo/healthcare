package assistant_healthcare.healthcare.Exception;

import org.springframework.http.HttpStatus;

public class BaseException extends RuntimeException {
    private final HttpStatus status;
    private final String error;

    public BaseException(HttpStatus status, String error, String message) {
        super(message);
        this.status = status;
        this.error = error;
    }
}
