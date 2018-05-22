package control.self.igor.dailywisdom.service.abstraction;

import control.self.igor.dailywisdom.model.authorization.Token;

public interface TokenService {

    Token createAccessToken(String username, String role);

    Token createRefreshToken(String username, String role);

    Token createAccessToken(String refreshToken);
}
