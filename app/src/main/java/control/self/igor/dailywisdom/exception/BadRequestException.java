package control.self.igor.dailywisdom.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    public BadRequestException() {

    }

    public BadRequestException(String message) {
	super(message);
    }

    public static <T> BadRequestException createEntityExistsException(Class<T> clazz) {
	return new BadRequestException(clazz.getSimpleName() + " already exists.");
    }

    public static <T> BadRequestException createEntityNotUniqueException(Class<T> clazz) {
	return new BadRequestException(clazz.getSimpleName() + " is not unique.");
    }

    public static BadRequestException createIncorrectTokenException() {
	return new BadRequestException("Given token is incorrect");
    }

}
