package control.self.igor.dailywisdom.controller.frontend.user;

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
import control.self.igor.dailywisdom.model.LoginResponse;
import control.self.igor.dailywisdom.model.Token;
import control.self.igor.dailywisdom.service.user.TokenService;
import control.self.igor.dailywisdom.service.user.UserService;
import io.jsonwebtoken.ExpiredJwtException;

@RestController
@RequestMapping("/user")
public class UserController {

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
	    exception.printStackTrace();
	    throw BadRequestException.createEntityExistsException(User.class);
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
		throw BadRequestException.createIncorrectTokenException();
	    }
	    return accessToken;
	} catch (UsernameNotFoundException | ExpiredJwtException exception) {
	    exception.printStackTrace();
	    throw new BadRequestException(exception.getMessage());
	}
    }
}
