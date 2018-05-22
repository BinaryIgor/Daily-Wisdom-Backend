package control.self.igor.dailywisdom.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BadRequestException() {

    }

    public BadRequestException(String message) {
	super(message);
    }

    public static <T> BadRequestException entityExists(Class<T> clazz) {
	return new BadRequestException(clazz.getSimpleName() + " already exists.");
    }

    public static BadRequestException incorrectTokenException() {
	return new BadRequestException("Given token is incorrect");
    }

}
