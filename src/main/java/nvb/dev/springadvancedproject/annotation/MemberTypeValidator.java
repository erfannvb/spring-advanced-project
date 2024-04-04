package nvb.dev.springadvancedproject.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class MemberTypeValidator implements ConstraintValidator<MemberType, String> {

    @Override
    public boolean isValid(String type, ConstraintValidatorContext constraintValidatorContext) {
        List<String> memberTypes = List.of("Gold", "Silver", "Bronze");
        return memberTypes.contains(type);
    }
}
