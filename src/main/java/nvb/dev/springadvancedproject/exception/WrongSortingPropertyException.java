package nvb.dev.springadvancedproject.exception;

public class WrongSortingPropertyException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Unable to sort by property '%s'. Use one of the following: %s.";

    public WrongSortingPropertyException(String wrongProperty, String validSortingProperties) {
        super(ERROR_MESSAGE.formatted(wrongProperty, validSortingProperties));
    }
}
