package control.self.igor.dailywisdom.security;

public class SecurityConstants {

    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 86_000_000L;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String SIGN_UP_URL = "/user/sign-up";
    public static final String LOGIN_URL = "/user/login";
    public static final String TOKEN_ROLE_CLAIM = "role";
}
