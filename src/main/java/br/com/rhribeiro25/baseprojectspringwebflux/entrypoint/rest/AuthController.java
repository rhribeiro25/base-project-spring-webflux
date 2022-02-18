package br.com.rhribeiro25.baseprojectspringwebflux.entrypoint.rest;

import br.com.rhribeiro25.baseprojectspringwebflux.core.entity.postgresql.UserEntity;
import br.com.rhribeiro25.baseprojectspringwebflux.core.useCases.AuthService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Class Authentication Controller
 *
 * @author Renan Henrique Ribeiro
 * @since 15/02/2022
 */
@RestController
@RequestMapping("api/auth")
@Validated
@Log4j2
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Mono<ResponseEntity> login(@RequestBody UserEntity user) {
        return authService.generateToken(user);
    }

    @PostMapping("/logout")
    public Mono logout() {
        Mono result = authService.saveTokenInBlacklist("Authorization");
        return result;
    }

}
