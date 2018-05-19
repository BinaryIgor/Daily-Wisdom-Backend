package control.self.igor.dailywisdom.service.implementation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import control.self.igor.dailywisdom.entity.User;
import control.self.igor.dailywisdom.entity.UserRole;
import control.self.igor.dailywisdom.repository.abstraction.UserRepository;
import control.self.igor.dailywisdom.service.abstraction.UserService;
import control.self.igor.dailywisdom.util.MockUtil;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserServiceTest {

    @TestConfiguration
    static class UserServiceTestConfiguration {

	@Autowired
	public UserRepository repository;

	@Bean
	public UserService userService() {
	    return new UserServiceImpl(repository);
	}
    }

    @Autowired
    public TestEntityManager entityManager;

    @Autowired
    public UserService userService;

    @Test
    public void authenticateUserTest() {
	String role = UserRole.Role.ADMIN.getTranslation();
	User user = entityManager.persist(MockUtil.createUser(role));
	assertTrue(userService.authenticate(user));
	assertFalse(userService.authenticate(MockUtil.createDifferentUser(user)));
    }

}
