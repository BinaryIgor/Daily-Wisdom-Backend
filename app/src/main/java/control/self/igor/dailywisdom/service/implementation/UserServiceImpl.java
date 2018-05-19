package control.self.igor.dailywisdom.service.implementation;

import java.util.Collections;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public User authenticate(User user) {
	User foundUser = userRepository.findByNameIgnoreCase(user.getName());
	if (foundUser == null || !foundUser.getPassword().equals(user.getPassword())) {
	    throw new NoSuchElementException();
	}
	return foundUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
	User user = userRepository.findByNameIgnoreCase(username);
	if (user == null) {
	    throw new UsernameNotFoundException(username);
	}
	return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(),
		Collections.singletonList(new SimpleGrantedAuthority(user.getUserRole().getRole())));
    }
}
