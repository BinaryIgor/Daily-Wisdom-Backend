package control.self.igor.dailywisdom.model;

public class LoginResponse {

    private String role;
    private Token accessToken;
    private Token refreshToken;

    public LoginResponse(String role, Token accessToken, Token refreshToken) {
	this.role = role;
	this.accessToken = accessToken;
	this.refreshToken = refreshToken;
    }

    public String getRole() {
	return role;
    }

    public Token getAccessToken() {
	return accessToken;
    }

    public Token getRefreshToken() {
	return refreshToken;
    }

}
