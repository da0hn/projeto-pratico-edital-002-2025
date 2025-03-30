package br.com.ghonda.core.annotations.validators;

import br.com.ghonda.core.annotations.Enumerator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.stream.Stream;

public class EnumeratorValidator implements ConstraintValidator<Enumerator, Enum<?>> {

    private List<String> acceptedValues;

    private boolean optional;

    @Override
    public void initialize(final Enumerator constraintAnnotation) {
        this.acceptedValues = Stream.of(constraintAnnotation.enumClass().getEnumConstants())
            .map(Enum::name)
            .toList();
        this.optional = constraintAnnotation.optional();
    }

    @Override
    public boolean isValid(final Enum<?> value, final ConstraintValidatorContext context) {
        if (this.optional && value == null) {
            return true;
        }
        if (value == null) {
            return false;
        }

        final var isValid = this.acceptedValues.contains(value.name());

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("O valor informado precisa ser um dos seguintes: " + String.join(", ", this.acceptedValues))
                .addConstraintViolation();
        }

        return isValid;
    }

}
