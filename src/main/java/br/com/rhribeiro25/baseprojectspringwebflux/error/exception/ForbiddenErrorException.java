package br.com.rhribeiro25.baseprojectspringwebflux.error.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Class Forbidden Error Exception
 *
 * @author Renan Henrique Ribeiro
 * @since 06/02/2021
 */
@Log4j2
@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenErrorException extends Throwable {

    private static final long serialVersionUID = -769147147883245023L;

    public ForbiddenErrorException(String msg) {
        super(msg);
    }

    public ForbiddenErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ForbiddenErrorException(Throwable cause) {
        super(cause);
    }

}