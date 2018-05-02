package control.self.igor.dailywisdom.service.abstraction;

import control.self.igor.dailywisdom.entity.User;

public interface UserService {

    User getUser(String name);

    boolean authenticate(User user);
}
