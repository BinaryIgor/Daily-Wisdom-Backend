package control.self.igor.dailywisdom.security;

import java.io.IOException;
import java.util.Collections;
import java.util.logging.Logger;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import control.self.igor.dailywisdom.entity.User;
import control.self.igor.dailywisdom.model.LoginResponse;
import control.self.igor.dailywisdom.model.Token;
import control.self.igor.dailywisdom.service.json.JsonService;
import control.self.igor.dailywisdom.service.stream.StreamService;
import control.self.igor.dailywisdom.service.user.TokenService;
import control.self.igor.dailywisdom.service.user.UserService;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger LOGGER = Logger.getLogger(JwtAuthenticationFilter.class.getSimpleName());
    private AuthenticationManager authenticationManager;
    private UserService userService;
    private JsonService jsonService;
    private TokenService tokenService;
    private StreamService streamService;

    @Autowired
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, UserService userService,
	    JsonService jsonService, TokenService tokenService, StreamService streamService) {
	this.authenticationManager = authenticationManager;
	this.userService = userService;
	this.jsonService = jsonService;
	this.tokenService = tokenService;
	this.streamService = streamService;
	setFilterProcessesUrl(SecurityConstants.LOGIN_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
	    throws AuthenticationException {
	try {
	    User user = jsonService.deserialize(request.getInputStream(), User.class);
	    String role = userService.getUserRoleByName(user.getName());
	    return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getName(),
		    user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(role))));
	} catch (IOException | NullPointerException exception) {
	    exception.printStackTrace();
	    throw new RuntimeException(exception);
	}
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
	    Authentication authentication) throws IOException {
	String username = ((org.springframework.security.core.userdetails.User) authentication.getPrincipal())
		.getUsername();
	String role = ((org.springframework.security.core.userdetails.User) authentication.getPrincipal())
		.getAuthorities().iterator().next().getAuthority();
	LOGGER.info(username + " logged in as " + role);
	Token accessToken = tokenService.createAccessToken(username, role);
	Token refreshToken = tokenService.createRefreshToken(username, role);
	LoginResponse loginResponse = new LoginResponse(role, accessToken, refreshToken);
	streamService.writeBytesToOutputStream(response.getOutputStream(),
		jsonService.serialize(loginResponse).getBytes());
    }

}
