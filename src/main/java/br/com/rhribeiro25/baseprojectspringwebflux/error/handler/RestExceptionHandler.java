package br.com.rhribeiro25.baseprojectspringwebflux.error.handler;

import br.com.rhribeiro25.baseprojectspringwebflux.error.Error;
import br.com.rhribeiro25.baseprojectspringwebflux.error.*;
import br.com.rhribeiro25.baseprojectspringwebflux.error.exception.BadRequestErrorException;
import br.com.rhribeiro25.baseprojectspringwebflux.error.exception.InternalServerErrorException;
import br.com.rhribeiro25.baseprojectspringwebflux.error.exception.NotFoundErrorException;
import br.com.rhribeiro25.baseprojectspringwebflux.error.exception.UnauthorizedErrorException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.webjars.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.util.*;

/**
 * Class Rest Exception Handler
 *
 * @author Renan Henrique Ribeiro
 * @since 06/02/2021
 */
@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    private ObjectMapper mapper;

    public RestExceptionHandler() {
        mapper = new ObjectMapper();
    }

    @ExceptionHandler(WebClientResponseException.BadRequest.class)
    public ResponseEntity<?> handlerBadRequestException(WebClientResponseException ex) throws JsonProcessingException {
        Error error = Error.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .error(ErrorDetails.builder()
                        .name("BadRequestError")
                        .message(ex.getMessage())
                        .time(new Date().getTime())
                        .build())
                .build();
        log.error(mapper.writeValueAsString(error));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handlerNotFoundException(NotFoundException ex) throws JsonProcessingException {
        Error error = Error.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .error(ErrorDetails.builder()
                        .name("NotFoundError")
                        .message(ex.getMessage())
                        .time(new Date().getTime())
                        .build())
                .build();
        log.error(mapper.writeValueAsString(error));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<?> handlerInternalServerErrorException(InternalServerErrorException ex) throws JsonProcessingException {
        String msg =  messageSource.getMessage("message.internal.error", null, Locale.getDefault());
        Error error = Error.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(ErrorDetails.builder()
                        .name("ServerError")
                        .message(msg)
                        .time(new Date().getTime())
                        .build())
                .build();
        log.error(mapper.writeValueAsString(error));
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handlerConstraintViolationException(ConstraintViolationException ex) throws JsonProcessingException {
        List<ParamErrorDetails> params = ValidationError.getParamErrorDetails(ex);
        String msg =  messageSource.getMessage("message.error.param", null, Locale.getDefault());
        Error error = Error.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .error(ErrorDetails.builder()
                        .name("InvalidParamError")
                        .message(msg)
                        .time(new Date().getTime())
                        .build())
                .params(params)
                .build();
        log.error(mapper.writeValueAsString(error));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundErrorException.class)
    public ResponseEntity<?> handlerNotFoundServerErrorException(NotFoundErrorException ex) throws JsonProcessingException {
        Error error = Error.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .error(ErrorDetails.builder()
                        .name("NotFoundError")
                        .message(ex.getMessage())
                        .time(new Date().getTime())
                        .build())
                .build();
        log.error(mapper.writeValueAsString(error));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestErrorException.class)
    public ResponseEntity<?> handlerBadRequestErrorException(BadRequestErrorException ex) throws JsonProcessingException {
        Error error = Error.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .error(ErrorDetails.builder()
                        .name("BadRequestError")
                        .message(ex.getMessage())
                        .time(new Date().getTime())
                        .build())
                .build();
        log.error(mapper.writeValueAsString(error));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {WebExchangeBindException.class})
    public ResponseEntity<Object> handleJacksonError(WebExchangeBindException ex) throws JsonProcessingException {
        List<ParamErrorDetails> params = ValidationError.getParamErrorDetails(ex);
        String msg =  messageSource.getMessage("message.error.param", null, Locale.getDefault());
        Error error = Error.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .error(ErrorDetails.builder()
                        .name("InvalidParamError")
                        .message(msg)
                        .time(new Date().getTime())
                        .build())
                .params(params)
                .build();
        log.error(mapper.writeValueAsString(error));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedErrorException.class)
    public ResponseEntity<?> handlerBadRequestErrorException(UnauthorizedErrorException ex) throws JsonProcessingException {
        Error error = Error.builder()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .error(ErrorDetails.builder()
                        .name("UnauthorizedError")
                        .message(ex.getMessage())
                        .time(new Date().getTime())
                        .build())
                .build();
        log.error(mapper.writeValueAsString(error));
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(WebClientErrorResponseException.class)
    public ResponseEntity<?> handlerWebClientErrorResponseException(WebClientErrorResponseException ex) {
        return new ResponseEntity<>(ex, HttpStatus.valueOf(ex.getStatusCode()));
    }
}