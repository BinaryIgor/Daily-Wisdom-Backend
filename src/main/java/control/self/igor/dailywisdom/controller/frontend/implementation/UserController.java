package control.self.igor.dailywisdom.controller.frontend.implementation;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import control.self.igor.dailywisdom.entity.User;
import control.self.igor.dailywisdom.exception.NotFoundException;
import control.self.igor.dailywisdom.model.api.Response;
import control.self.igor.dailywisdom.model.authorization.Token;
import control.self.igor.dailywisdom.service.abstraction.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
	this.userService = userService;
    }

    @PostMapping("/login")
    public Token login(@Valid @RequestBody User user) {
	if (!userService.authenticate(user)) {
	    throw new NotFoundException();
	}
	return new Token("mock", "mock", System.currentTimeMillis() + 24 * 3600 * 1000);
    }

    // TODO logic implementation
    @PostMapping("/logout")
    public Response logout(@Valid @RequestBody User user) {
	if (!userService.authenticate(user)) {
	    throw new NotFoundException();
	}
	return new Response(Response.OK);
    }
}
