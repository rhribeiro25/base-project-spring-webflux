package br.com.rhribeiro25.baseprojectspringwebflux.core.constraints;

import br.com.rhribeiro25.baseprojectspringwebflux.utils.StringUtils;

import javax.validation.ConstraintValidatorContext;

/**
 * Constraint Validator created to handle annotations of type {@link PhoneConstraint} to be able to enable custom validations
 *
 * @author Renan Henrique Ribeiro
 * @since 06/20/2021
 */
public class PhoneConstraintValidator implements GeneticConstraint<PhoneConstraint, String> {

    private boolean REQUIRE;

    @Override
    public void initialize(PhoneConstraint constrain) {
        REQUIRE = constrain.require();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (validating(context, REQUIRE && StringUtils.isNullOrBlank(value), "{message.error.phone.not.blank}")) return false;
        else if(StringUtils.isNotNullAndBlank(value)) {
            if (validating(context, value.trim().length() < 11, "{message.error.phone.min.size}")) return false;
            if (validating(context, value.trim().length() > 23, "{message.error.phone.max.size}")) return false;
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

