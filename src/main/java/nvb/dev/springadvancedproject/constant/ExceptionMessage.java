package nvb.dev.springadvancedproject.constant;

public class ExceptionMessage {

    private ExceptionMessage() {
    }

    public static final String USERNAME_NOT_FOUND = "Username '%s' does not exist.";
    public static final String USER_NOT_FOUND = "User not found.";
    public static final String WRONG_PASSWORD = "Old password is incorrect.";
    public static final String PASSWORD_NOT_MATCH = "New Password and Confirm Password do not match.";

    public static final String ENTITY_NOT_STORABLE = "There was an error in storing the data.";

    public static final String NO_AUTHORS_FOUND = "There is no author in the database.";

    public static final String NO_BOOKS_FOUND = "There is no book in the database.";

    public static final String NO_MEMBERS_FOUND = "There is no member in the database";

}
