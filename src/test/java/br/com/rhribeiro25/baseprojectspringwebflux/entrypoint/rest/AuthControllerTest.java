package br.com.rhribeiro25.baseprojectspringwebflux.entrypoint.rest;

import br.com.rhribeiro25.baseprojectspringwebflux.core.document.AuthDocument;
import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.bpswf.request.AuthRequestCreator;
import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.bpswf.request.UserRequestLogin;
import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.generic.response.ObjectResponse;
import br.com.rhribeiro25.baseprojectspringwebflux.core.useCases.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(SpringExtension.class)
public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthService authService;

    private final UserRequestLogin userRequestLogin = AuthRequestCreator.createUserRequestLogin();
    private final AuthDocument authDocument = AuthRequestCreator.createAuthDocument();


    @BeforeEach
    public void setUp() {

        BDDMockito.mock(AuthController.class);

        BDDMockito.given(authService.generateToken(userRequestLogin))
                .willReturn(Mono.just(ObjectResponse.builder()
                        .data("")
                        .statusCode(200)
                        .build()));

        BDDMockito.given(authService.saveTokenInBlacklist(authDocument))
                .willReturn(Mono.just(ObjectResponse.builder()
                        .data("Token bloqueado com sucesso!")
                        .statusCode(200)
                        .build()));
    }

    @Test
    @DisplayName("login -> Success With Result")
    public void loginSucessWithResult() {
        Mono result = authController.login(userRequestLogin);
        StepVerifier.create(result)
                .expectSubscription()
                .expectNext(ObjectResponse.builder()
                        .data("")
                        .statusCode(200)
                        .build())
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
                .expectNext(ObjectResponse.builder()
                        .data("Token bloqueado com sucesso!")
                        .statusCode(200)
                        .build())
                .verifyComplete();
    }

}
