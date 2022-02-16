package br.com.rhribeiro25.baseprojectspringwebflux.utils;

import br.com.rhribeiro25.baseprojectspringwebflux.error.Error;
import br.com.rhribeiro25.baseprojectspringwebflux.error.ErrorDetails;
import br.com.rhribeiro25.baseprojectspringwebflux.error.ExceptionResponse;
import br.com.rhribeiro25.baseprojectspringwebflux.error.WebClientErrorResponseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.Locale;

/**
 * Class Error Utils
 *
 * @author Renan Henrique Ribeiro
 * @since 06/02/2021
 */
@Log4j2
public class ErrorUtils {

    private static MessageSource messageSource = StaticContextUtils.getBean(MessageSource.class);
    private static String MSG_ERRO_500 = messageSource.getMessage("message.internal.server.error", null, Locale.getDefault());
    private static ObjectMapper mapper = new ObjectMapper();

    public static Mono<Void> responseFailed(ServerWebExchange exchange, int httpStatus, String msg, String name) throws JsonProcessingException {
        return responseWrite(exchange, httpStatus, msg, name);
    }

    private static Mono<Void> responseWrite(ServerWebExchange exchange, int httpStatus, String msg, String name) throws JsonProcessingException {
        if (httpStatus == 0) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR.value();
            msg = MSG_ERRO_500;
        }
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setAccessControlAllowCredentials(true);
        response.getHeaders().setAccessControlAllowOrigin("*");
        response.setStatusCode(HttpStatus.valueOf(httpStatus));
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
        DataBufferFactory dataBufferFactory = response.bufferFactory();
        ErrorDetails errorDetails = ErrorDetails.builder().name(name).message(msg).time(new Date().getTime()).build();
        Error errorResponse = Error.builder().statusCode(httpStatus).error(errorDetails).build();
        DataBuffer buffer = dataBufferFactory.wrap(mapper.writeValueAsString(errorResponse).getBytes(Charset.defaultCharset()));
        return response.writeWith(Mono.just(buffer)).doOnError((error) -> {
            DataBufferUtils.release(buffer);
        });
    }

    public static Mono<Throwable> getThrowableMono(ClientResponse response, String methodMsg) {
        return response.bodyToMono(ExceptionResponse.class).flatMap(resp -> {
            log.error(methodMsg, resp);
            return Mono.error(new WebClientErrorResponseException(resp.getStatusCode(), resp.getError(), resp.getParams()));
        });
    }
}
