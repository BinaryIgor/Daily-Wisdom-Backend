package control.self.igor.dailywisdom.model.authorization;

public class Token {

    private String accessToken;
    private String refreshToken;
    private long expirationDate;

    public Token(String accessToken, String refreshToken, long expirationDate) {
	this.accessToken = accessToken;
	this.refreshToken = refreshToken;
	this.expirationDate = expirationDate;
    }

    public String getAccessToken() {
	return accessToken;
    }

    public String getRefreshToken() {
	return refreshToken;
    }

    public long getExpirationDate() {
	return expirationDate;
    }

}
