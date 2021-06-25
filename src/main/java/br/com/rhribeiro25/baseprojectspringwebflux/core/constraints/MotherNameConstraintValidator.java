package br.com.rhribeiro25.baseprojectspringwebflux.core.constraints;

import br.com.rhribeiro25.baseprojectspringwebflux.utils.StringUtils;

import javax.validation.ConstraintValidatorContext;

/**
 * Constraint Validator created to handle annotations of type {@link MotherNameConstraint} to be able to enable custom validations
 *
 * @author Renan Henrique Ribeiro
 * @since 06/19/2021
 */
public class MotherNameConstraintValidator implements GeneticConstraint<MotherNameConstraint, String> {

    private boolean REQUIRE;
    private static final String REGEX_NOT_NUMBER = "^[^0-9]+$";
    private static final String REGEX_VALID_NAME = "^([\\w'\\-,.][^0-9_!¡?÷?¿/\\\\\\\\+=@#$%ˆ&*()\\\\{}|~<>;:[\\\\]]{2,}) ([\\w'\\-,.][^0-9_!¡?÷?¿/\\\\\\\\+=@#$%ˆ&*()\\\\{}|~<>;:[\\\\]]{2,})$";

    @Override
    public void initialize(MotherNameConstraint constrain) {
        REQUIRE = constrain.require();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (validating(context, REQUIRE && StringUtils.isNullOrBlank(value), "{message.error.mother.name.not.blank}")) return false;
        else if(StringUtils.isNotNullAndBlank(value)) {
            if (validating(context, !(value.trim().length() <= 255), "{message.error.mother.name.max.size}")) return false;
            if (validating(context, !value.matches(REGEX_NOT_NUMBER), "{message.error.mother.name.pattern.not.number}")) return false;
            if (validating(context, !value.matches(REGEX_VALID_NAME), "{message.error.mother.name.pattern}")) return false;
        }
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