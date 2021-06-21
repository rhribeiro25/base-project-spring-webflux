package br.com.rhribeiro25.baseprojectspringwebflux.core.constraints;

import javax.validation.ConstraintValidatorContext;

/**
 * Constraint Validator created to handle annotations of type {@link EmailConstraint} to be able to enable custom validations
 *
 * @author Renan Henrique Ribeiro
 * @since 06/20/2021
 */
public class EmailConstraintValidator implements GeneticConstraint<EmailConstraint, String> {

    private static final String REGEX_VALID_EMAIL = "^[\\w\\d.]+@[\\w\\d]+.[\\w\\d]+(.[\\w\\d]+)?$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (validating(context, (value == null || value.isBlank()), "{message.error.email.not.blank}")) return false;
        if (validating(context, value.trim().length() < 15, "{message.error.email.min.size}")) return false;
        if (validating(context, value.trim().length() > 255, "{message.error.email.max.size}")) return false;
        if (validating(context, !value.matches(REGEX_VALID_EMAIL), "{message.error.email.pattern.invalid}")) return false;
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

