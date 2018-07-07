package control.self.igor.dailywisdom.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ForbiddenException() {

    }

    private ForbiddenException(String message) {
	super(message);
    }

    public static ForbiddenException createAdminOnlyException() {
	return new ForbiddenException("Only admin can create, update and delete entities");
    }

}
