package nvb.dev.springadvancedproject.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MemberTypeValidator.class)
public @interface MemberType {

    String message() default "Invalid member type";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
