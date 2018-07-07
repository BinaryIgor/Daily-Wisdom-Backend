package control.self.igor.dailywisdom.service.validation;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import control.self.igor.dailywisdom.entity.UserRole.Role;

@Service
public class ValidationServiceImpl implements ValidationService {

    @Override
    public boolean validatePageRequest(Integer page, Integer size) {
	return (page == null && size == null) || ((page != null && page > 0 && size != null && size > 0))
		|| (page != null && page > 0 && size == null) || (page == null && size != null && size > 0);

    }

    @Override
    public boolean validateIds(long... ids) {
	if (ids == null || ids.length < 1) {
	    return true;
	}
	for (long id : ids) {
	    if (id < 1) {
		return false;
	    }
	}
	return true;
    }

    @Override
    public boolean validateRole(Authentication authentication, Role role) {
	if (authentication.getAuthorities().isEmpty()) {
	    return false;
	}
	for (GrantedAuthority authority : authentication.getAuthorities()) {
	    if (role.equalsByTranslation(authority.getAuthority())) {
		return true;
	    }
	}
	return false;
    }

}
