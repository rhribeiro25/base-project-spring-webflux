package br.com.rhribeiro25.baseprojectspringwebflux.core.useCases;

import br.com.rhribeiro25.baseprojectspringwebflux.config.security.JwtUtil;
import br.com.rhribeiro25.baseprojectspringwebflux.core.document.AuthDocument;
import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.bpswf.request.AuthRequestCreator;
import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.bpswf.request.UserRequestLogin;
import br.com.rhribeiro25.baseprojectspringwebflux.core.entity.UserEntity;
import br.com.rhribeiro25.baseprojectspringwebflux.core.entity.UserEntityCreator;
import br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.database.mongodb.BlackListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private UserService userService;

    @Mock
    private BlackListRepository blackListRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private PasswordEncoder encoder;

    private final UserEntity userEntity = UserEntityCreator.createUserEntity();

    private final UserRequestLogin userRequestLogin = AuthRequestCreator.createUserRequestLogin();
    private final AuthDocument authDocument = AuthRequestCreator.createAuthDocument();
    private HttpHeaders headers = new HttpHeaders();


    @BeforeEach
    public void setUp() {

        // findByEmail
        headers.set("Authorization", "token");
        BDDMockito.when(jwtUtil.generateToken(userEntity))
                .thenReturn("token");
        BDDMockito.when(encoder.matches(userEntity.getPassword(), userEntity.getPassword()))
                .thenReturn(true);
        BDDMockito.when(userService.findByEmail(userEntity.getEmail()))
                .thenReturn(Mono.just(userEntity));

        // existsByToken
        BDDMockito.when(blackListRepository.existsByToken(authDocument.getToken()))
                .thenReturn(Mono.just(false));
        BDDMockito.when(blackListRepository.save(authDocument))
                .thenReturn(Mono.just(authDocument));

    }

    @Test
    @DisplayName("login -> Success With Result")
    public void loginSucessWithResult() {
        Mono result = authService.generateToken(userRequestLogin);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "token");
        StepVerifier.create(result)
                .expectSubscription()
                .expectNext(headers)
                .verifyComplete();
    }

    @Test
    @DisplayName("logout -> Success With Result")
    public void logoutSucessWithResult() {
        Mono result = authService.saveTokenInBlacklist(authDocument);
        StepVerifier.create(result)
                .expectSubscription()
                .expectNext(authDocument)
                .verifyComplete();
    }

}
