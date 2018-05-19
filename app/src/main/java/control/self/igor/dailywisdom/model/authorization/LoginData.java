package control.self.igor.dailywisdom.model.authorization;

public class LoginData {

    private String role;
    private Token token;

    public LoginData(String role, Token token) {
	this.role = role;
	this.token = token;
    }

    public String getRole() {
	return role;
    }

    public Token getToken() {
	return token;
    }

}
