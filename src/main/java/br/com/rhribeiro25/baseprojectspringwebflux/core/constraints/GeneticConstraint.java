package br.com.rhribeiro25.baseprojectspringwebflux.core.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

public interface GeneticConstraint<A extends Annotation, T> extends ConstraintValidator<A, T> {
    boolean validating(ConstraintValidatorContext context, boolean validation, String msg);
}
