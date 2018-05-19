package control.self.igor.dailywisdom.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import control.self.igor.dailywisdom.entity.User;
import control.self.igor.dailywisdom.repository.abstraction.UserRepository;
import control.self.igor.dailywisdom.service.abstraction.UserService;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
	this.userRepository = userRepository;
    }

    @Override
    public User getUser(String name) {
	return userRepository.findByNameIgnoreCase(name);
    }

    @Override
    public boolean authenticate(User user) {
	User foundUser = userRepository.findByNameIgnoreCase(user.getName());
	if (foundUser == null) {
	    return false;
	}
	return foundUser.getPassword().equals(user.getPassword());
    }

}
