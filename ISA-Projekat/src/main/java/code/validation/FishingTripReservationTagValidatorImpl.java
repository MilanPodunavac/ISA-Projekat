package code.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FishingTripReservationTagValidatorImpl implements ConstraintValidator<FishingTripReservationTagValidator, Set<String>> {
    List<String> valueList = null;

    @Override
    public boolean isValid(Set<String> values, ConstraintValidatorContext constraintValidatorContext) {
        if (values == null)
            return true;

        for (String value : values) {
            if (value != null && valueList.contains(value.toUpperCase())) {
                continue;
            }
            return false;
        }
        return true;
    }

    @Override
    public void initialize(FishingTripReservationTagValidator constraintAnnotation) {
        valueList = new ArrayList<>();
        Class<? extends Enum<?>> enumClass = constraintAnnotation.targetClassType();

        @SuppressWarnings("rawtypes")
        Enum[] enumValArr = enumClass.getEnumConstants();

        for (@SuppressWarnings("rawtypes") Enum enumVal : enumValArr) {
            valueList.add(enumVal.toString().toUpperCase());
        }
    }
}