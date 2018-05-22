package control.self.igor.dailywisdom.service.implementation;

import java.util.Collections;
import java.util.NoSuchElementException;

import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import control.self.igor.dailywisdom.entity.User;
import control.self.igor.dailywisdom.repository.abstraction.UserRepository;
import control.self.igor.dailywisdom.service.abstraction.UserService;

@Service
@Transactional
// TODO user exists?
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
	this.userRepository = userRepository;
	this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User getUser(String name) {
	return userRepository.findByName(name);
    }

    @Override
    public User authenticate(User user) {
	User foundUser = userRepository.findByName(user.getName());
	System.out.println("Found user = " + foundUser);
	String encodedPassword = bCryptPasswordEncoder.encode(foundUser.getPassword());
	if (foundUser == null || !foundUser.getPassword().equals(encodedPassword)) {
	    throw new NoSuchElementException();
	}
	return foundUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
	User user = userRepository.findByName(username);
	if (user == null) {
	    throw new UsernameNotFoundException(username);
	}
	return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(),
		Collections.singletonList(new SimpleGrantedAuthority(user.getUserRole().getRole())));
    }

    @Override
    public User signUp(User user) {
	User oldUser = userRepository.findByName(user.getName());
	if (oldUser != null) {
	    throw new EntityExistsException();
	}
	user.setUserRole(userRepository.getGuest());
	user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
	return userRepository.save(user);
    }

    @Override
    public String getUserRoleByName(String name) {
	return userRepository.getUserRoleByName(name);
    }
}
