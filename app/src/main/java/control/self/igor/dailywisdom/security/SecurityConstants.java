package control.self.igor.dailywisdom.security;

public class SecurityConstants {

    public static final String SECRET = "SecGeneRetRator";
    public static final long ACCES_TOKEN_EXPIRATION_TIME = 3_600_000L;
    public static final long REFRESH_TOKEN_EXPIRATION_TIME = 604_800_000L;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String SIGN_UP_URL = "/user/sign-up";
    public static final String LOGIN_URL = "/user/login";
    public static final String TOKEN_ROLE_CLAIM = "role";
    public static final String TOKEN_TYPE_KEY = "tokenType";
    public static final String[] PUBLIC_ENDPOINTS_PATTERNS = new String[] { "/data/**", "/user/**", "/v2/api-docs",
	    "/swagger-resources", "/swagger-resources/**", "/configuration/ui", "/configuration/security",
	    "/swagger-ui.html", "/webjars/**" };

    public enum TokenType {
	ACCESS_TOKEN("accessToken"), REFRESH_TOKEN("refreshToken");

	private String value;

	TokenType(String value) {
	    this.value = value;
	}

	public String getValue() {
	    return value;
	}

	public static boolean isAccessToken(String tokenType) {
	    return ACCESS_TOKEN.value.equals(tokenType);
	}
    }
}
