package control.self.igor.dailywisdom.service.user;

import org.springframework.security.core.userdetails.UserDetailsService;

import control.self.igor.dailywisdom.entity.User;

public interface UserService extends UserDetailsService {

    User signUp(User user);

    User getUser(String name);

    User authenticate(User user);

    String getUserRoleByName(String name);
}
