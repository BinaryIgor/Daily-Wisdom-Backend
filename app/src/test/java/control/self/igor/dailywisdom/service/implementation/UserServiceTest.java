package control.self.igor.dailywisdom.service.implementation;

import java.util.NoSuchElementException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import control.self.igor.dailywisdom.entity.User;
import control.self.igor.dailywisdom.entity.UserRole;
import control.self.igor.dailywisdom.repository.UserRepository;
import control.self.igor.dailywisdom.service.user.UserService;
import control.self.igor.dailywisdom.service.user.UserServiceImpl;
import control.self.igor.dailywisdom.util.MockUtil;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserServiceTest {

    @TestConfiguration
    static class UserServiceTestConfiguration {

	@Autowired
	public UserRepository repository;

	@Autowired
	public BCryptPasswordEncoder bCryptPasswordEncoder;

	@Bean
	public UserService userService() {
	    return new UserServiceImpl(repository, bCryptPasswordEncoder);
	}
    }

    @Autowired
    public TestEntityManager entityManager;

    @Autowired
    public UserService userService;

    @Test(expected = NoSuchElementException.class)
    public void authenticateUserTest() {
	String role = UserRole.Role.ADMIN.getTranslation();
	User user = entityManager.persist(MockUtil.createGuestUser());
	userService.authenticate(user);
	userService.authenticate(MockUtil.createDifferentUser(user));
    }

}
