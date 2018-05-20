package control.self.igor.dailywisdom.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import control.self.igor.dailywisdom.entity.User;
import control.self.igor.dailywisdom.model.authorization.TokenData;
import control.self.igor.dailywisdom.service.abstraction.JsonService;
import control.self.igor.dailywisdom.service.abstraction.StreamService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger LOGGER = Logger.getLogger(JwtAuthenticationFilter.class.getSimpleName());
    private AuthenticationManager authenticationManager;
    private JsonService jsonService;
    private StreamService streamService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JsonService jsonService,
	    StreamService streamService) {
	this.authenticationManager = authenticationManager;
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
	    LOGGER.info("Have logged user = " + user.getName());
	    return authenticationManager.authenticate(
		    new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword(), new ArrayList<>()));
	} catch (IOException exception) {
	    throw new RuntimeException(exception);
	}
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
	    Authentication authentication) throws IOException {
	String username = ((org.springframework.security.core.userdetails.User) authentication.getPrincipal())
		.getUsername();
	long expirationDate = System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME;
	String token = Jwts.builder().setSubject(username).setExpiration(new Date(expirationDate))
		.signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET.getBytes()).compact();
	TokenData tokenData = new TokenData(token, token, expirationDate);
	streamService.writeBytesToOutputStream(response.getOutputStream(), jsonService.serialize(tokenData).getBytes());
    }

}
