package br.com.rhribeiro25.baseprojectspringwebflux.error;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

/**
 * Class Exception Response
 *
 * @author Renan Henrique Ribeiro
 * @since 06/02/2021
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper=false)
public class ExceptionResponse  {
    private HttpStatus status;
    private Integer statusCode;
    private Object error;
    private Object params;
}