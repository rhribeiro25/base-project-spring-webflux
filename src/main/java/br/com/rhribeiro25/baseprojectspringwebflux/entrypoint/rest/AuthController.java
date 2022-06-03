package br.com.rhribeiro25.baseprojectspringwebflux.entrypoint.rest;

import br.com.rhribeiro25.baseprojectspringwebflux.core.document.AuthDocument;
import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.bpswf.request.UserRequestLogin;
import br.com.rhribeiro25.baseprojectspringwebflux.core.useCases.AuthService;
import br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.adapter.generic.GenericConverter;
import io.netty.handler.codec.Headers;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Locale;
import java.util.Map;

/**
 * Class Authentication Controller
 *
 * @author Renan Henrique Ribeiro
 * @since 15/02/2022
 */
@RestController
@RequestMapping("api/auths")
@Validated
@Log4j2
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private GenericConverter genericConverter;

    @Autowired
    private MessageSource messageSource;

    @PostMapping("/login")
    public Mono login(@RequestBody UserRequestLogin user) {
        Mono headersMono = authService.generateToken(user);
        return headersMono.flatMap(headers -> genericConverter.converterMonoToObjectResponse(headers, HttpStatus.OK));
    }

    @PostMapping("/logout")
    public Mono logout(@NotNull @RequestHeader Map<String, String> headers) {
        AuthDocument auth = AuthDocument.builder().token(headers.get("Authorization").replace("Bearer ", "")).build();
        authService.saveTokenInBlacklist(auth);
        return genericConverter.converterMonoToObjectResponse(messageSource.getMessage("message.token.blocked.successfully", null, Locale.getDefault()), HttpStatus.OK);
    }

}
