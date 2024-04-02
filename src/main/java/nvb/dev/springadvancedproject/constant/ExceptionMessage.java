package nvb.dev.springadvancedproject.constant;

public class ExceptionMessage {

    private ExceptionMessage() {
    }

    public static final String USERNAME_NOT_FOUND = "Username '%s' does not exist.";
    public static final String USER_NOT_FOUND = "User not found.";

    public static final String ENTITY_NOT_STORABLE = "There was an error in storing the data.";

    public static final String AUTHOR_NOT_FOUND = "Author not found.";
    public static final String NO_AUTHORS_FOUND = "There is no author in the database.";

    public static final String BOOK_NOT_FOUND = "Book not found.";
    public static final String NO_BOOKS_FOUND = "There is no book in the database.";

}
