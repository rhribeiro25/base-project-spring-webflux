package br.com.rhribeiro25.baseprojectspringwebflux.error.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Class Bad Request Error Exception
 *
 * @author Renan Henrique Ribeiro
 * @since 06/02/2021
 */
@Log4j2
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestErrorException extends Throwable {

    private static final long serialVersionUID = -769147155483245023L;

    public BadRequestErrorException(String msg) {
        super(msg);
    }

    public BadRequestErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadRequestErrorException(Throwable cause) {
        super(cause);
    }

}