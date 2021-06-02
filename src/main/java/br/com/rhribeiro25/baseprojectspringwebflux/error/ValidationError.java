package br.com.rhribeiro25.baseprojectspringwebflux.error;

import br.com.rhribeiro25.baseprojectspringwebflux.utils.StaticContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.support.WebExchangeBindException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.*;

/**
 * Class Validation Error
 *
 * @author Renan Henrique Ribeiro
 * @since 06/02/2021
 */
@Slf4j
public class ValidationError {

    public static List<ParamErrorDetails> getParamErrorDetails(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> violation = ex.getConstraintViolations();
        List<String> field = new ArrayList<>();
        List<ParamErrorDetails> params = new ArrayList<>();
        violation.forEach(x ->{
            Iterator<Path.Node> nodes = x.getPropertyPath().iterator();
            Path.Node firstNode = nodes.next();
            Path.Node secondNode = nodes.next();
            params.add(ParamErrorDetails.builder()
                    .value(x.getInvalidValue() != null ? x.getInvalidValue().toString() :"")
                    .message(x.getMessage())
                    .property(secondNode.getName())
                    .build());
        });
        return params;
    }

    public static List<ParamErrorDetails> getParamErrorDetails(WebExchangeBindException ex) {
        List<FieldError> allErrors = ex.getFieldErrors();
        List<ParamErrorDetails> params = new ArrayList<>();
        allErrors.forEach(x ->{
            params.add(ParamErrorDetails.builder()
                    .value(getValue(x))
                    .message(messageSource(x))
                    .property(x.getField())
                    .build());
        });
        return params;
    }

    private static String getValue(FieldError x) {
        return x.getRejectedValue() != null ? x.getRejectedValue().toString() : null;
    }

    private static String messageSource(FieldError x) {
        String code = new StringBuffer("message.error.").
                append(x.getField().replaceAll("\\[(.*?)\\]", "").toLowerCase())
                .append(".")
                .append(x.getCode().toLowerCase())
                .toString();
        String msg = "";
        MessageSource messageSource = StaticContextUtils.getBean(MessageSource.class);
        try {
            msg = messageSource.getMessage(code, null, Locale.getDefault());
        } catch (NoSuchMessageException e){
            msg = getMessageDefaultOrCustomized(x, messageSource);
        }
        return msg;
    }

    private static String getMessageDefaultOrCustomized(FieldError x, MessageSource messageSource) {
        String msg;
        if(x.getDefaultMessage().contains("{") && x.getDefaultMessage().contains("}")) {
            msg = messageSource.getMessage(x.getDefaultMessage().replaceAll("\\{", "").replaceAll("\\}", ""), null, Locale.getDefault());
        } else {
            msg = x.getDefaultMessage();
        }
        return msg;
    }
}
