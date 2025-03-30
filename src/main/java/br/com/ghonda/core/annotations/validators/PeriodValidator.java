package br.com.ghonda.core.annotations.validators;

import br.com.ghonda.core.annotations.Period;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
public class PeriodValidator implements ConstraintValidator<Period, Object> {

    private String beginDateField;

    private String endDateField;

    private boolean canBeEqual;

    @Override
    public void initialize(final Period constraintAnnotation) {
        this.beginDateField = constraintAnnotation.beginDate();
        this.endDateField = constraintAnnotation.endDate();
        this.canBeEqual = constraintAnnotation.canBeEqual();
    }

    @Override
    public boolean isValid(final Object target, final ConstraintValidatorContext context) {
        try {
            final var beginDate = this.getFieldValue(target, this.beginDateField);
            final var endDate = this.getFieldValue(target, this.endDateField);

            if (beginDate == null || endDate == null) {
                return false;
            }

            if (this.canBeEqual) {
                return endDate.isEqual(beginDate) || endDate.isAfter(beginDate);
            }

            return endDate.isAfter(beginDate);
        }
        catch (final Exception exception) {
            log.error(
                "Ocorreu um erro inesperado ao tentar válidar o periodo de {} até {}",
                this.beginDateField,
                this.endDateField,
                exception
            );
            return false;
        }
    }

    private LocalDate getFieldValue(final Object target, final String fieldName) throws Exception {
        try {
            return (LocalDate) target.getClass().getMethod(fieldName).invoke(target);
        }
        catch (final NoSuchMethodException e) {
            final var field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return (LocalDate) field.get(target);
        }
    }

}
