package control.self.igor.dailywisdom.service.abstraction;

import org.springframework.security.core.userdetails.UserDetailsService;

import control.self.igor.dailywisdom.entity.User;

public interface UserService extends UserDetailsService {

    User getUser(String name);

    User authenticate(User user);
}
