package control.self.igor.dailywisdom.security;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
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
import control.self.igor.dailywisdom.model.authorization.LoginResponse;
import control.self.igor.dailywisdom.model.authorization.TokenData;
import control.self.igor.dailywisdom.service.abstraction.JsonService;
import control.self.igor.dailywisdom.service.abstraction.StreamService;
import control.self.igor.dailywisdom.service.abstraction.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger LOGGER = Logger.getLogger(JwtAuthenticationFilter.class.getSimpleName());
    private AuthenticationManager authenticationManager;
    private UserService userService;
    private JsonService jsonService;
    private StreamService streamService;

    @Autowired
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, UserService userService,
	    JsonService jsonService, StreamService streamService) {
	this.authenticationManager = authenticationManager;
	this.userService = userService;
	this.jsonService = jsonService;
	this.streamService = streamService;
	setFilterProcessesUrl(SecurityConstants.LOGIN_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
	    throws AuthenticationException {
	try {
	    LOGGER.info("Authentication trial...");
	    User user = jsonService.deserialize(request.getInputStream(), User.class);
	    String role = userService.getUserRoleByName(user.getName());
	    LOGGER.info("Have logged user = " + user.getName());
	    LOGGER.info("With role = " + role);
	    return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getName(),
		    user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(role))));
	} catch (IOException exception) {
	    throw new RuntimeException(exception);
	}
    }

    // TODO refresh token
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
	    Authentication authentication) throws IOException {
	String username = ((org.springframework.security.core.userdetails.User) authentication.getPrincipal())
		.getUsername();
	String role = ((org.springframework.security.core.userdetails.User) authentication.getPrincipal())
		.getAuthorities().iterator().next().getAuthority();
	LOGGER.info("Injecting role...." + role);
	long expirationDate = System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME;
	String token = Jwts.builder().setSubject(username).claim(SecurityConstants.TOKEN_ROLE_CLAIM, role)
		.setExpiration(new Date(expirationDate))
		.signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET.getBytes()).compact();
	LoginResponse loginResponse = new LoginResponse(role, new TokenData(token, token, expirationDate));
	streamService.writeBytesToOutputStream(response.getOutputStream(),
		jsonService.serialize(loginResponse).getBytes());
    }

}
