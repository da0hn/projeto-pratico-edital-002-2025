package br.com.ghonda.core.annotations;

import br.com.ghonda.core.annotations.validators.EnumeratorValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({
    java.lang.annotation.ElementType.FIELD,
    java.lang.annotation.ElementType.METHOD,
    java.lang.annotation.ElementType.PARAMETER,
    java.lang.annotation.ElementType.ANNOTATION_TYPE
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = EnumeratorValidator.class)
public @interface Enumerator {

    Class<? extends Enum<?>> enumClass();

    String message() default "O valor informado não é válido para o enumerador {enumClass}";

    boolean optional() default false;

    Class<?>[] groups() default {};

    Class<? extends Annotation>[] payload() default {};

}
