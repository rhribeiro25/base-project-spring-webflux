package br.com.rhribeiro25.baseprojectspringwebflux.core.constraints;

import javax.validation.ConstraintValidatorContext;

/**
 * Constraint Validator created to handle annotations of type {@link CepConstraint} to be able to enable custom validations
 *
 * @author Renan Henrique Ribeiro
 * @since 06/02/2021
 */
public class CepConstraintValidator implements GeneticConstraint<CepConstraint, String> {

    private static final String CEP_FORMAT = "^\\d{5}-\\d{3}$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (validating(context, (value == null || value.isBlank()), "{message.error.cep.not.blank}")) return false;
        if (validating(context, value.trim().length() != 9, "{message.error.cep.size}")) return false;
        if (validating(context, !value.matches(CEP_FORMAT), "{message.error.cep.format}")) return false;
        return true;
    }

    public boolean validating(ConstraintValidatorContext context, boolean validation, String msg) {
        if (validation) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(msg).addConstraintViolation();
            return true;
        }
        return false;
    }
}

