package assistant_healthcare.healthcare.Exception;

import org.springframework.http.HttpStatus;

public class ImageException extends BaseException {
    public ImageException(String error, String message) {
        super(HttpStatus.NOT_FOUND, error, message);
    }
}
