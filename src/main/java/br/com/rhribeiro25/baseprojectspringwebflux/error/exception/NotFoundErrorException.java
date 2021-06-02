package br.com.rhribeiro25.baseprojectspringwebflux.error.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Class Not Found Error Exception
 *
 * @author Renan Henrique Ribeiro
 * @since 06/02/2021
 */
@Slf4j
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundErrorException extends Throwable {

    private static final long serialVersionUID = -769147155483245022L;

    public NotFoundErrorException(String msg) {
        super(msg);
    }

    public NotFoundErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundErrorException(Throwable cause) {
        super(cause);
    }

}