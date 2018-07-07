package control.self.igor.dailywisdom.service.user;

import control.self.igor.dailywisdom.model.Token;

public interface TokenService {

    Token createAccessToken(String username, String role);

    Token createRefreshToken(String username, String role);

    Token createAccessToken(String refreshToken);
}
