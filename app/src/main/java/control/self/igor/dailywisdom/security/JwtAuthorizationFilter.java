package control.self.igor.dailywisdom.security;

import java.io.IOException;
import java.util.Collections;
import java.util.logging.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import control.self.igor.dailywisdom.exception.UnauthorizedException;
import control.self.igor.dailywisdom.security.SecurityConstants.TokenType;
import control.self.igor.dailywisdom.service.stream.StreamService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private static final Logger LOGGER = Logger.getLogger(JwtAuthorizationFilter.class.getSimpleName());
    private StreamService streamService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, StreamService streamService) {
	super(authenticationManager);
	this.streamService = streamService;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
	    throws IOException, ServletException {
	LOGGER.info("Filtering...");
	String authorizationHeader = request.getHeader(SecurityConstants.AUTHORIZATION_HEADER);
	if (authorizationHeader == null || authorizationHeader.isEmpty()) {
	    chain.doFilter(request, response);
	    return;
	}
	try {
	    UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
	    SecurityContextHolder.getContext().setAuthentication(authentication);
	    chain.doFilter(request, response);
	} catch (Exception exception) {
	    exception.printStackTrace();
	    response.setStatus(HttpStatus.UNAUTHORIZED.value());
	    streamService.writeBytesToOutputStream(response.getOutputStream(), exception.toString().getBytes());
	}
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
	String token = request.getHeader(SecurityConstants.AUTHORIZATION_HEADER);
	if (token == null) {
	    throw new UnauthorizedException();
	}
	Claims claims = Jwts.parser().setSigningKey(SecurityConstants.SECRET.getBytes())
		.parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, "")).getBody();
	String user = claims.getSubject();
	String role = claims.get(SecurityConstants.TOKEN_ROLE_CLAIM, String.class);
	String tokenType = claims.get(SecurityConstants.TOKEN_TYPE_KEY, String.class);
	if (user == null || tokenType == null || !TokenType.isAccessToken(tokenType)) {
	    throw new UnauthorizedException();
	}
	return new UsernamePasswordAuthenticationToken(user, null,
		Collections.singletonList(new SimpleGrantedAuthority(role)));
    }
}
