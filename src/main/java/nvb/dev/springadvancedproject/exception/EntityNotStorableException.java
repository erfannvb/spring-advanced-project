package nvb.dev.springadvancedproject.exception;

import static nvb.dev.springadvancedproject.constant.ExceptionMessage.ENTITY_NOT_STORABLE;

public class EntityNotStorableException extends RuntimeException {

    public EntityNotStorableException() {
        super(ENTITY_NOT_STORABLE);
    }
}
