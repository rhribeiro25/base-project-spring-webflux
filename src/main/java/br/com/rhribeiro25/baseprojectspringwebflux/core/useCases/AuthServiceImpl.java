package br.com.rhribeiro25.baseprojectspringwebflux.core.useCases;

import br.com.rhribeiro25.baseprojectspringwebflux.config.security.JwtUtil;
import br.com.rhribeiro25.baseprojectspringwebflux.core.entity.UserEntity;
import br.com.rhribeiro25.baseprojectspringwebflux.core.entity.AuthEntity;
import br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.database.BlackListRepository;
import br.com.rhribeiro25.baseprojectspringwebflux.error.exception.BadRequestErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Locale;

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

    @Autowired
    private UserService userService;

    @Autowired
    private BlackListRepository blackListRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final MessageSource messageSource;

    private final ResponseEntity<Object> UNAUTHORIZED = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    private final PasswordEncoder encoder;

    public Mono generateToken(UserEntity user) {
        return userService.findByEmail(user.getEmail()).map(userDb -> {

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", jwtUtil.generateToken(userDb));

            return encoder.matches(user.getPassword(), userDb.getPassword()) ? new ResponseEntity<>("", headers, HttpStatus.OK) : UNAUTHORIZED;
        });
    }

    public Mono saveTokenInBlacklist(AuthEntity auth) {
        return blackListRepository.existsByToken(auth.getToken()).flatMap(exists -> {
            if (!exists) return blackListRepository.save(auth).map(token ->
                    ResponseEntity.ok(messageSource.getMessage("message.token.blocked.successfully", null, Locale.getDefault())));
            else return Mono.empty();
        }).switchIfEmpty(Mono.error(new BadRequestErrorException(messageSource.getMessage("message.bad.request.error.find.token", null, Locale.getDefault()))));
    }

    public Flux findAll() {
        return blackListRepository.findAll();
    }

}
