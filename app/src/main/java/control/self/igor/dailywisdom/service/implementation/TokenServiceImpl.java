package control.self.igor.dailywisdom.service.implementation;

import java.util.Date;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import control.self.igor.dailywisdom.entity.User;
import control.self.igor.dailywisdom.model.authorization.Token;
import control.self.igor.dailywisdom.security.SecurityConstants;
import control.self.igor.dailywisdom.security.SecurityConstants.TokenType;
import control.self.igor.dailywisdom.service.abstraction.TokenService;
import control.self.igor.dailywisdom.service.abstraction.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenServiceImpl implements TokenService {

    private UserService userService;

    public TokenServiceImpl(UserService userService) {
	this.userService = userService;
    }

    @Override
    public Token createAccessToken(String username, String role) {
	return createToken(username, role, TokenType.ACCESS_TOKEN);
    }

    @Override
    public Token createRefreshToken(String username, String role) {
	return createToken(username, role, TokenType.REFRESH_TOKEN);
    }

    private Token createToken(String username, String role, TokenType tokenType) {
	long expirationDate = System.currentTimeMillis();
	if (TokenType.ACCESS_TOKEN == tokenType) {
	    expirationDate += SecurityConstants.ACCES_TOKEN_EXPIRATION_TIME;
	} else {
	    expirationDate += SecurityConstants.REFRESH_TOKEN_EXPIRATION_TIME;
	}
	String token = Jwts.builder().setSubject(username).claim(SecurityConstants.TOKEN_ROLE_CLAIM, role)
		.claim(SecurityConstants.TOKEN_TYPE_KEY, tokenType.getValue()).setExpiration(new Date(expirationDate))
		.signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET.getBytes()).compact();
	return new Token(token, expirationDate);
    }

    @Override
    public Token createAccessToken(String refreshToken) {
	Claims claims = Jwts.parser().setSigningKey(SecurityConstants.SECRET.getBytes()).parseClaimsJws(refreshToken)
		.getBody();
	String username = claims.getSubject();
	String role = claims.get(SecurityConstants.TOKEN_ROLE_CLAIM, String.class);
	String tokenType = claims.get(SecurityConstants.TOKEN_TYPE_KEY, String.class);
	System.out.println("TokenServiceImpl.createAccessToken()");
	System.out.println("TokenType:" + tokenType);
	if (username == null || tokenType == null || TokenType.isAccessToken(tokenType)) {
	    return null;
	}
	User user = userService.getUser(username);
	if (user == null) {
	    throw new UsernameNotFoundException(username);
	}
	return createToken(username, role, TokenType.ACCESS_TOKEN);
    }
}
