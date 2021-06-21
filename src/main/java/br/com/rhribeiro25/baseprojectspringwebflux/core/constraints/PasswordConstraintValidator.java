package br.com.rhribeiro25.baseprojectspringwebflux.core.constraints;

import javax.validation.ConstraintValidatorContext;

/**
 * Constraint Validator created to handle annotations of type {@link PasswordConstraint} to be able to enable custom validations
 *
 * @author Renan Henrique Ribeiro
 * @since 06/20/2021
 */
public class PasswordConstraintValidator implements GeneticConstraint<PasswordConstraint, String> {

    private static final String REGEX_VALID_PASSWORD = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[$*&@#])[0-9a-zA-Z$*&@#]{8,}$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (validating(context, (value == null || value.isBlank()), "{message.error.password.not.blank}")) return false;
        if (validating(context, !value.matches(REGEX_VALID_PASSWORD), "{message.error.password.pattern}")) return false;
        return true;
    }

    @Override
    public boolean validating(ConstraintValidatorContext context, boolean validation, String msg) {
        if (validation) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(msg).addConstraintViolation();
            return true;
        }
        return false;
    }
}

