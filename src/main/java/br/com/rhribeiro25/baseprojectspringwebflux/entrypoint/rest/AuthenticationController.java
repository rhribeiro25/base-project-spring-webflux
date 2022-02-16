package br.com.rhribeiro25.baseprojectspringwebflux.entrypoint.rest;

import br.com.rhribeiro25.baseprojectspringwebflux.config.security.JwtUtil;
import br.com.rhribeiro25.baseprojectspringwebflux.core.entity.UserEntity;
import br.com.rhribeiro25.baseprojectspringwebflux.core.useCases.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Objects;

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
public class AuthenticationController {

    private final static ResponseEntity<Object> UNAUTHORIZED =
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public Mono<ResponseEntity> login(@RequestBody UserEntity user) {
        return userService.findByEmail(user.getEmail()).map(userDb ->
                Objects.equals(user.getPassword(), userDb.getPassword())
                        ? ResponseEntity.ok(jwtUtil.generateToken(userDb))
                        : UNAUTHORIZED
        );
    }

}
