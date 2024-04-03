package nvb.dev.springadvancedproject.constant;

public class Constant {

    private Constant() {
    }

    public static final String USER_URL = "/api/user/**";
    public static final String AUTH_URL = "/api/auth/**";
    public static final String AUTHOR_URL = "/api/author/**";
    public static final String BOOK_URL = "/api/book/**";
    public static final String MEMBER_URL = "/api/member/**";

    public static final int TOKEN_EXPIRATION_TIME = 1000 * 60 * 24;
    public static final int REFRESH_TOKEN_EXPIRATION_TIME = 604800000;

    public static final String BEARER = "Bearer ";
    public static final String SECRET_KEY = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";

}
