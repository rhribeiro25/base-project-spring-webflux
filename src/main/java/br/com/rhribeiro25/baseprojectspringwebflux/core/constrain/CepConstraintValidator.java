package br.com.rhribeiro25.baseprojectspringwebflux.core.constrain;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * ConstraintValidator created to handle annotations of type {@link CepConstraint} to be able to enable custom validations
 *
 * @author Renan Henrique Ribeiro
 * @since 06/02/2021
 */
public class CepConstraintValidator implements ConstraintValidator<CepConstraint, String> {

    private CepConstraint constraintAnnotation;
    private static final String CEP_FORMAT = "^\\d{5}-\\d{3}$";

    @Override
    public void initialize(CepConstraint constraintAnnotation) {
        this.constraintAnnotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (validRegistryNumber(context, (value == null || value.isBlank()), "{message.error.cep.not.blank}"))
            return false;

        if (validRegistryNumber(context, value.trim().length() > 9, "{message.error.cep.max.size}")) return false;

        if (validRegistryNumber(context, value.trim().length() < 9, "{message.error.cep.min.size}")) return false;

        if (validRegistryNumber(context, !value.matches(CEP_FORMAT), "{message.error.cep.format}")) return false;

        return true;
    }

    private boolean validRegistryNumber(ConstraintValidatorContext context, boolean validation, String msg) {
        if (validation) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(msg).addConstraintViolation();
            return true;
        }
        return false;
    }
}

