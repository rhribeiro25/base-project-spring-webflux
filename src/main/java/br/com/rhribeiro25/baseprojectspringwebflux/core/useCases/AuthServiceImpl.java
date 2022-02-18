package br.com.rhribeiro25.baseprojectspringwebflux.core.useCases;

import br.com.rhribeiro25.baseprojectspringwebflux.config.security.JwtUtil;
import br.com.rhribeiro25.baseprojectspringwebflux.core.entity.postgresql.UserEntity;
import br.com.rhribeiro25.baseprojectspringwebflux.core.entity.redis.TokenEntity;
import br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.database.redis.AuthRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Class Auth Service
 *
 * @author Renan Henrique Ribeiro
 * @since 16/02/2022
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private ResponseEntity<Object> UNAUTHORIZED = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    @Autowired
    private UserService userService;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final MessageSource messageSource;

    private final PasswordEncoder encoder;

    public Mono generateToken(UserEntity user) {
        return userService.findByEmail(user.getEmail()).map(userDb -> {

                    HttpHeaders headers = new HttpHeaders();
                    headers.set("Authorization", jwtUtil.generateToken(userDb));

                    return encoder.matches(user.getPassword(), userDb.getPassword())
                            ? new ResponseEntity<>("", headers, HttpStatus.OK)
                            : UNAUTHORIZED;
                }
        );
    }

    public Mono saveTokenInBlacklist(String token) {
        TokenEntity newToken = TokenEntity.builder()
                .token(token)
                .build();
        return authRepository.save(newToken);
    }

}
