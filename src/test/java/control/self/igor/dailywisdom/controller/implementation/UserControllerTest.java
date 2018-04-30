package control.self.igor.dailywisdom.controller.implementation;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import control.self.igor.dailywisdom.controller.frontend.implementation.UserController;
import control.self.igor.dailywisdom.entity.User;
import control.self.igor.dailywisdom.entity.UserRole;
import control.self.igor.dailywisdom.service.abstraction.JsonService;
import control.self.igor.dailywisdom.service.abstraction.UserService;
import control.self.igor.dailywisdom.service.implementation.JsonServiceImpl;
import control.self.igor.dailywisdom.util.MockUtil;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @TestConfiguration
    public static class UserControllerTestConfiguration {

	@Bean
	public UserService userService() {
	    return Mockito.mock(UserService.class);
	}

	@Bean
	public JsonService jsonService() {
	    return new JsonServiceImpl();
	}
    }

    private final static String BASE_URL = "/user/";

    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonService jsonService;

    @Test
    public void loginTest() throws Exception {
	logInOutTest(true);
    }

    @Test
    public void logoutTest() throws Exception {
	logInOutTest(false);
    }

    private void logInOutTest(boolean login) throws Exception {
	String url = BASE_URL;
	if (login) {
	    url += "login";
	} else {
	    url += "logout";
	}
	User user = MockUtil.createUser(UserRole.Role.admin.getTranslation());
	when(userService.authenticate(ArgumentMatchers.any(User.class))).thenReturn(true);
	mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(jsonService.serialize(user)))
		.andExpect(status().isOk());
	when(userService.authenticate(ArgumentMatchers.any(User.class))).thenReturn(false);
	mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(jsonService.serialize(user)))
		.andExpect(status().isNotFound());
    }

}
