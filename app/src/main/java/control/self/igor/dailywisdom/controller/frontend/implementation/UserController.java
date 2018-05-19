package control.self.igor.dailywisdom.controller.frontend.implementation;

import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import control.self.igor.dailywisdom.entity.User;
import control.self.igor.dailywisdom.exception.UnauthorizedException;
import control.self.igor.dailywisdom.model.api.Response;
import control.self.igor.dailywisdom.model.authorization.LoginData;
import control.self.igor.dailywisdom.model.authorization.Token;
import control.self.igor.dailywisdom.service.abstraction.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger LOGGER = Logger.getLogger(UserController.class.getSimpleName());
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
	this.userService = userService;
    }

    @PostMapping("/login")
    public LoginData login(@Valid @RequestBody User user) {
	try {
	    user = userService.authenticate(user);
	} catch (NoSuchElementException exception) {
	    LOGGER.log(Level.WARNING, exception.toString(), exception);
	    throw new UnauthorizedException();
	}
	return new LoginData(user.getUserRole().getRole(),
		new Token("mock", "mock", System.currentTimeMillis() + 24 * 3600 * 1000));
    }

    @GetMapping("/get")
    public User getUser() {
	return new User();
    }

    // TODO logic implementation
    @PostMapping("/logout")
    public Response logout(@Valid @RequestBody User user) {
	try {
	    user = userService.authenticate(user);
	} catch (NoSuchElementException exception) {
	    LOGGER.log(Level.WARNING, exception.toString(), exception);
	    throw new UnauthorizedException();
	}
	return new Response(Response.OK);
    }
}
