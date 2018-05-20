package control.self.igor.dailywisdom.service.abstraction;

import org.springframework.security.core.userdetails.UserDetailsService;

import control.self.igor.dailywisdom.entity.User;
import control.self.igor.dailywisdom.entity.UserRole;

public interface UserService extends UserDetailsService {

    User signUp(User user);

    User getUser(String name);

    User authenticate(User user);

    UserRole getUserRoleByName(String name);
}
