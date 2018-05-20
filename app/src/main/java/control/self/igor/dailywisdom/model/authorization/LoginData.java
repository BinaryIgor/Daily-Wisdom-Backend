package control.self.igor.dailywisdom.model.authorization;

public class LoginData {

    private String role;
    private TokenData token;

    public LoginData(String role, TokenData token) {
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
