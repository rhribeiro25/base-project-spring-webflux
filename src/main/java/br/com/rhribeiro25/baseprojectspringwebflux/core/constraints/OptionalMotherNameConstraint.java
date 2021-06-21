package br.com.rhribeiro25.baseprojectspringwebflux.core.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Constraint created to be able to enable custom validations
 *
 * @author Renan Henrique Ribeiro
 * @since 06/19/2021
 */
@Constraint(validatedBy = OptionalMotherNameConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.CONSTRUCTOR,
        ElementType.FIELD,
        ElementType.TYPE,
        ElementType.METHOD,
        ElementType.PARAMETER})
public @interface OptionalMotherNameConstraint {
    String message() default "{message.error.name.default}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}