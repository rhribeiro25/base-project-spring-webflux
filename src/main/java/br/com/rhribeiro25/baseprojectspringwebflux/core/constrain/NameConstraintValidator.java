package br.com.rhribeiro25.baseprojectspringwebflux.core.constrain;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import javax.validation.ConstraintValidatorContext;
import java.util.Locale;

/**
 * ConstraintValidator criada tratar anotações do tipo {@link NameConstraint} para ser possível habilitar validações customizadas
 *
 * @author Renan Henrique Ribeiro
 * @since 06/19/2021
 */
public class NameConstraintValidator implements GeneticConstraint<NameConstraint, String> {

    @Autowired
    private MessageSource messageSource;

    private String PERSON = null;
    private String FIELD = null;
    private static final String REGEX_NOT_NUMBER = "^[^0-9]+$";
    private static final String REGEX_VALID_NAME = "^([\\w'\\-,.][^0-9_!¡?÷?¿/\\\\\\\\+=@#$%ˆ&*()\\\\{}|~<>;:[\\\\]]{2,})$";

    @Override
    public void initialize(NameConstraint constrain) {
        PERSON = constrain.person();
        FIELD = constrain.field();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (validating(context, FIELD == null, "message.error.name.default")) return false;
        if (validating(context, (value == null || value.isBlank()), "message.error." + FIELD + ".name.notblank"))
            return false;
        if (validating(context, !(value.trim().length() <= 127), "message.error." + FIELD + ".name.size")) return false;
        if (validating(context, !value.matches(REGEX_NOT_NUMBER), "message.error." + FIELD + ".name.pattern.not.number"))
            return false;
        if (validating(context, !value.matches(REGEX_VALID_NAME), "message.error." + FIELD + ".name.pattern"))
            return false;
        return true;
    }

    public boolean validating(ConstraintValidatorContext context, boolean validation, String msg) {
        if (validation) {
            HibernateConstraintValidatorContext hibernateContext = context.unwrap(HibernateConstraintValidatorContext.class);
            hibernateContext.disableDefaultConstraintViolation();
            hibernateContext.addExpressionVariable("person", PERSON)
                    .buildConstraintViolationWithTemplate(messageSource.getMessage(msg, null, Locale.getDefault()))
                    .addConstraintViolation();
            return true;
        }
        return false;
    }
}
