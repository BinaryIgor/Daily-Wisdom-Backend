package control.self.igor.dailywisdom.controller.frontend.implementation;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityExistsException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import control.self.igor.dailywisdom.entity.User;
import control.self.igor.dailywisdom.exception.BadRequestException;
import control.self.igor.dailywisdom.model.authorization.LoginResponse;
import control.self.igor.dailywisdom.model.authorization.Token;
import control.self.igor.dailywisdom.service.abstraction.TokenService;
import control.self.igor.dailywisdom.service.abstraction.UserService;
import io.jsonwebtoken.ExpiredJwtException;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger LOGGER = Logger.getLogger(UserController.class.getSimpleName());
    private UserService userService;
    private TokenService tokenService;

    @Autowired
    public UserController(UserService userService, TokenService tokenService) {
	this.userService = userService;
	this.tokenService = tokenService;
    }

    @PostMapping("/sign-up")
    public LoginResponse signUp(@Valid @RequestBody User user) {
	try {
	    userService.signUp(user);
	} catch (EntityExistsException exception) {
	    LOGGER.log(Level.WARNING, exception.toString(), exception);
	    throw BadRequestException.entityExists(User.class);
	}
	Token accessToken = tokenService.createAccessToken(user.getName(), user.getUserRole().getRole());
	Token refreshToken = tokenService.createRefreshToken(user.getName(), user.getUserRole().getRole());
	return new LoginResponse(user.getUserRole().getRole(), accessToken, refreshToken);
    }

    @PostMapping("/token/refresh")
    public Token refreshToken(@Valid @RequestBody Token refreshToken) {
	try {
	    Token accessToken = tokenService.createAccessToken(refreshToken.getValue());
	    if (accessToken == null) {
		throw BadRequestException.incorrectTokenException();
	    }
	    return accessToken;
	} catch (UsernameNotFoundException | ExpiredJwtException exception) {
	    LOGGER.log(Level.WARNING, exception.toString(), exception);
	    throw new BadRequestException(exception.getMessage());
	}
    }
}
