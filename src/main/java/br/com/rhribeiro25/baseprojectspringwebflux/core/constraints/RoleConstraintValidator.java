package br.com.rhribeiro25.baseprojectspringwebflux.core.constraints;

import br.com.rhribeiro25.baseprojectspringwebflux.utils.StringUtils;

import javax.validation.ConstraintValidatorContext;

/**
 * Constraint Validator created to handle annotations of type {@link RoleConstraint} to be able to enable custom validations
 *
 * @author Renan Henrique Ribeiro
 * @since 06/02/2021
 */
public class RoleConstraintValidator implements GeneticConstraint<RoleConstraint, String> {

    private static final String ROLE_FORMAT = "^ADMIN|COORDINATOR|LEADER|INTERN$";
    private boolean REQUIRE;

    @Override
    public void initialize(RoleConstraint constrain) {
        REQUIRE = constrain.require();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (validating(context, REQUIRE && StringUtils.isNullOrBlank(value), "{message.error.role.not.blank}")) return false;
        else if(StringUtils.isNotNullAndBlank(value)) {
            if (validating(context, value.trim().length() > 63, "{message.error.role.max.size}")) return false;
            if (validating(context, !value.matches(ROLE_FORMAT), "{message.error.role.pattern}")) return false;
        }
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

