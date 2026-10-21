package assistant_healthcare.healthcare.Exception;

import org.springframework.http.HttpStatus;

public class TextException extends BaseException {
    public TextException(String error, String status) {
        super(HttpStatus.NOT_FOUND, error, status);
    }
}
