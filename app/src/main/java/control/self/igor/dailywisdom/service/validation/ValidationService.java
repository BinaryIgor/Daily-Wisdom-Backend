package control.self.igor.dailywisdom.service.validation;

import org.springframework.security.core.Authentication;

import control.self.igor.dailywisdom.entity.UserRole;

public interface ValidationService {
    boolean validatePageRequest(Integer page, Integer size);

    boolean validateIds(long... ids);

    boolean validateRole(Authentication authentication, UserRole.Role role);
}
