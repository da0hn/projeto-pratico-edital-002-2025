package br.com.ghonda.core.annotations;

import br.com.ghonda.core.annotations.validators.PeriodValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PeriodValidator.class)
public @interface Period {

    String message() default "O periodo informado inválido";

    Class<?>[] groups() default {};

    Class<? extends Annotation>[] payload() default {};

    String beginDate();

    String endDate();

    boolean canBeEqual() default false;


}
