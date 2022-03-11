package br.com.rhribeiro25.baseprojectspringwebflux.entrypoint.rest;

import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.bpswf.request.UserRequestCreator;
import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.bpswf.request.UserRequestPatch;
import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.bpswf.request.UserRequestPost;
import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.bpswf.request.UserRequestPut;
import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.generic.response.ObjectResponse;
import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.generic.response.PaginatorResponse;
import br.com.rhribeiro25.baseprojectspringwebflux.core.useCases.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private final Object objectResponse = ObjectResponse.builder().build();
    private final Object paginatorResponse = PaginatorResponse.builder().build();
    private final UserRequestPatch userRequestPatch = UserRequestCreator.createUserRequestPatch();
    private final UserRequestPost userRequestPost = UserRequestCreator.createUserRequestPost();
    private final UserRequestPut userRequestPut = UserRequestCreator.createUserRequestPut();


    @BeforeEach
    public void setUp() {

        BDDMockito.mock(UserController.class);

        BDDMockito.given(userService.findById(1L))
                .willReturn(Mono.just(ObjectResponse.builder()
                        .data(objectResponse)
                        .statusCode(200)
                        .build()));

        BDDMockito.given(userService.findAll(PageRequest.of(0, 5)))
                .willReturn(Mono.just(ObjectResponse.builder()
                        .data(paginatorResponse)
                        .statusCode(200)
                        .build()));

        BDDMockito.given(userService.save(userRequestPost))
                .willReturn(Mono.just(ObjectResponse.builder()
                        .data(objectResponse)
                        .statusCode(201)
                        .build()));

        BDDMockito.given(userService.updateByPut(userRequestPut))
                .willReturn(Mono.just(ObjectResponse.builder()
                        .data(objectResponse)
                        .statusCode(200)
                        .build()));

        BDDMockito.given(userService.updateByPatch(1L, userRequestPatch))
                .willReturn(Mono.just(ObjectResponse.builder()
                        .data(objectResponse)
                        .statusCode(200)
                        .build()));

        BDDMockito.given(userService.delete(1L))
                .willReturn(Mono.just(ObjectResponse.builder()
                        .data("Usuário deletado com sucesso!")
                        .statusCode(200)
                        .build()));
    }

    @Test
    @DisplayName("find User By Id -> Success With Result")
    public void findByIdUserSucessWithResult() {
        Mono result = userController.findById(1l);
        StepVerifier.create(result)
                .expectSubscription()
                .expectNext(ObjectResponse.builder()
                        .data(objectResponse)
                        .statusCode(200)
                        .build())
                .verifyComplete();
    }

    @Test
    @DisplayName("find All User  -> Success With Result")
    public void findAllUserSucessWithResult() {
        Mono result = userController.findAll(0, 5);
        StepVerifier.create(result)
                .expectSubscription()
                .expectNext(ObjectResponse.builder()
                        .data(paginatorResponse)
                        .statusCode(200)
                        .build())
                .verifyComplete();
    }

    @Test
    @DisplayName("create User  -> Success With Result")
    public void createUserSucessWithResult() {
        Mono result = userController.save(userRequestPost);
        StepVerifier.create(result)
                .expectSubscription()
                .expectNext(ObjectResponse.builder()
                        .data(objectResponse)
                        .statusCode(201)
                        .build())
                .verifyComplete();
    }

    @Test
    @DisplayName("update all fields of User -> Success With Result")
    public void updateUserByPutSucessWithResult() {
        Mono result = userController.updateByPut(userRequestPut);
        StepVerifier.create(result)
                .expectSubscription()
                .expectNext(ObjectResponse.builder()
                        .data(objectResponse)
                        .statusCode(200)
                        .build())
                .verifyComplete();
    }

    @Test
    @DisplayName("update specific field of User -> Success With Result")
    public void updateUserByPatchSucessWithResult() {
        Mono result = userController.updateByPatch(1L, userRequestPatch);
        StepVerifier.create(result)
                .expectSubscription()
                .expectNext(ObjectResponse.builder()
                        .data(objectResponse)
                        .statusCode(200)
                        .build())
                .verifyComplete();
    }

    @Test
    @DisplayName("delete User -> Success With Result")
    public void deleteUserSucessWithResult() {
        Mono result = userController.delete(1l);

        StepVerifier.create(result)
                .expectSubscription()
                .expectNext(ObjectResponse.builder()
                        .data("Usuário deletado com sucesso!")
                        .statusCode(200)
                        .build())
                .verifyComplete();
    }
}
