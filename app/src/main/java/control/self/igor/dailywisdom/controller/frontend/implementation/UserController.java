package control.self.igor.dailywisdom.controller.frontend.implementation;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityExistsException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import control.self.igor.dailywisdom.entity.User;
import control.self.igor.dailywisdom.exception.BadRequestException;
import control.self.igor.dailywisdom.model.api.Response;
import control.self.igor.dailywisdom.model.authorization.LoginResponse;
import control.self.igor.dailywisdom.model.authorization.TokenData;
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

    @PostMapping("/sign-up")
    public LoginResponse signUp(@Valid @RequestBody User user) {
	try {
	    userService.signUp(user);
	} catch (EntityExistsException exception) {
	    LOGGER.log(Level.WARNING, exception.toString(), exception);
	    throw BadRequestException.entityExists(User.class);
	}
	return new LoginResponse(user.getUserRole().getRole(),
		new TokenData("mock", "mock", System.currentTimeMillis() + 24 * 3600 * 1000));
    }

    @GetMapping("/test")
    public Response test() {
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	System.out.println("Username: " + authentication.getName());
	if (authentication.getAuthorities().isEmpty()) {
	    System.out.println("User does not have any authorithies!");
	} else {
	    for (GrantedAuthority authorithy : authentication.getAuthorities()) {
		System.out.println("authorithy: " + authorithy.getAuthority());
	    }
	}
	return new Response("dada");
    }
}
