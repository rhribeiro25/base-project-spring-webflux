package br.com.rhribeiro25.baseprojectspringwebflux.core.useCases;

import br.com.rhribeiro25.baseprojectspringwebflux.config.security.JwtUtil;
import br.com.rhribeiro25.baseprojectspringwebflux.core.document.AuthDocument;
import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.bpswf.request.UserRequestLogin;
import br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.adapter.generic.GenericConverter;
import br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.database.mongodb.BlackListRepository;
import br.com.rhribeiro25.baseprojectspringwebflux.error.exception.BadRequestErrorException;
import br.com.rhribeiro25.baseprojectspringwebflux.error.exception.ForbiddenErrorException;
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
    private MessageSource messageSource;

    @Autowired
    private UserService userService;

    @Autowired
    private GenericConverter genericConverter;

    @Autowired
    private BlackListRepository blackListRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder encoder;

    public Mono generateToken(UserRequestLogin user) {
        return userService.findByEmail(user.getEmail()).map(userDb -> {

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", jwtUtil.generateToken(userDb));

            if (encoder.matches(user.getPassword(), userDb.getPassword()))
                return headers;
            else
                return Mono.error(new ForbiddenErrorException(messageSource.getMessage("message.forbidden.error.incorrect.credentials", null, Locale.getDefault())));
        });
    }


    public Mono saveTokenInBlacklist(AuthDocument auth) {
        return blackListRepository.existsByToken(auth.getToken()).flatMap(exists -> {
            if (!exists)
                return blackListRepository.save(auth);
            else return Mono.error(new BadRequestErrorException(messageSource.getMessage("message.bad.request.error.find.token", null, Locale.getDefault())));
        });
    }

}
