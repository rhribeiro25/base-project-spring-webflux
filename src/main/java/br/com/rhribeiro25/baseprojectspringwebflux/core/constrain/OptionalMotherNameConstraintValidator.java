package br.com.rhribeiro25.baseprojectspringwebflux.core.constrain;

import javax.validation.ConstraintValidatorContext;

/**
 * ConstraintValidator criada tratar anotações do tipo {@link OptionalMotherNameConstraint} para ser possível habilitar validações customizadas
 *
 * @author Renan Henrique Ribeiro
 * @since 06/19/2021
 */
public class OptionalMotherNameConstraintValidator implements GeneticConstraint<OptionalMotherNameConstraint, String> {

    private static final String REGEX_NOT_NUMBER = "^[^0-9]+$";
    private static final String REGEX_VALID_NAME = "^([\\w'\\-,.][^0-9_!¡?÷?¿/\\\\\\\\+=@#$%ˆ&*()\\\\{}|~<>;:[\\\\]]{2,}) ([\\w'\\-,.][^0-9_!¡?÷?¿/\\\\\\\\+=@#$%ˆ&*()\\\\{}|~<>;:[\\\\]]{2,})$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) return true;
        if (validating(context, !(value.trim().length() <= 127), "{message.error.mother.name.size}")) return false;
        if (validating(context, !value.matches(REGEX_NOT_NUMBER), "{message.error.mother.name.pattern.not.number}")) return false;
        if (validating(context, !value.matches(REGEX_VALID_NAME), "{message.error.mother.name.pattern}")) return false;
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
