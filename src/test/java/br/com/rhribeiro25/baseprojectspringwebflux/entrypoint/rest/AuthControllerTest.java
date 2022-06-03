package br.com.rhribeiro25.baseprojectspringwebflux.entrypoint.rest;

import br.com.rhribeiro25.baseprojectspringwebflux.core.document.AuthDocument;
import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.bpswf.request.AuthRequestCreator;
import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.bpswf.request.UserRequestLogin;
import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.generic.response.ObjectResponse;
import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.generic.response.ObjectResponseCreator;
import br.com.rhribeiro25.baseprojectspringwebflux.core.entity.UserEntity;
import br.com.rhribeiro25.baseprojectspringwebflux.core.entity.UserEntityCreator;
import br.com.rhribeiro25.baseprojectspringwebflux.core.useCases.AuthServiceImpl;
import br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.adapter.generic.GenericConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@ExtendWith(SpringExtension.class)
public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthServiceImpl authService;

    @Mock
    private GenericConverter genericConverter;

    @Mock
    private MessageSource messageSource;

    private final UserRequestLogin userRequestLogin = AuthRequestCreator.createUserRequestLogin();
    private final AuthDocument authDocument = AuthRequestCreator.createAuthDocument();
    private final UserEntity userEntity = UserEntityCreator.createUserEntity();
    private final ObjectResponse objectResponse = ObjectResponseCreator.createObjectResponse("Token bloqueado com sucesso!", HttpStatus.OK);
    private ResponseEntity responseEntity;
    private HttpHeaders headers = new HttpHeaders();


    @BeforeEach
    public void setUp() {

        headers.set("Authorization", "token");
        responseEntity = new ResponseEntity(headers, HttpStatus.OK);
        BDDMockito.when(authService.generateToken(userRequestLogin))
                .thenReturn(Mono.just(headers));

        BDDMockito.when(messageSource.getMessage("message.token.blocked.successfully", null, Locale.getDefault()))
                .thenReturn("Token bloqueado com sucesso!");
        BDDMockito.when(genericConverter.converterMonoToObjectResponse(messageSource.getMessage("message.token.blocked.successfully", null, Locale.getDefault()), HttpStatus.OK))
                .thenReturn(Mono.just(objectResponse));
        BDDMockito.when(authService.saveTokenInBlacklist(authDocument))
                .thenReturn(Mono.just(userEntity));
    }

    @Test
    @DisplayName("login -> Success With Result")
    public void loginSucessWithResult() {
        Mono result = authController.login(userRequestLogin);
        StepVerifier.create(result)
                .expectSubscription()
                .expectNext(responseEntity)
                .verifyComplete();
    }

    @Test
    @DisplayName("logout -> Success With Result")
    public void logoutSucessWithResult() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "token");

        Mono result = authController.logout(headers);
        StepVerifier.create(result)
                .expectSubscription()
                .expectNext(objectResponse)
                .verifyComplete();
    }

}
