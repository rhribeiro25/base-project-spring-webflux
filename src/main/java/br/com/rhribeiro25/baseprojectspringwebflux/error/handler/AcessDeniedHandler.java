package br.com.rhribeiro25.baseprojectspringwebflux.error.handler;

import br.com.rhribeiro25.baseprojectspringwebflux.utils.ErrorUtils;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Locale;

/**
 * Class Access Denied Handler
 *
 * @author Renan Henrique Ribeiro
 * @since 06/28/2021
 */
@Log4j2
@Component
public class AcessDeniedHandler implements ServerAccessDeniedHandler {

    @Autowired
    private MessageSource messageSource;

    @SneakyThrows
    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, AccessDeniedException denied) {
        return  ErrorUtils.responseFailed(serverWebExchange, HttpStatus.FORBIDDEN.value(),
                messageSource.getMessage("message.error.forbidden", null, Locale.getDefault()), "ForbiddenError");
    }
}
