package control.self.igor.dailywisdom.model.authorization;

public class LoginResponse {

    private String role;
    private TokenData token;

    public LoginResponse(String role, TokenData token) {
	this.role = role;
	this.token = token;
    }

    public String getRole() {
	return role;
    }

    public TokenData getToken() {
	return token;
    }

}
