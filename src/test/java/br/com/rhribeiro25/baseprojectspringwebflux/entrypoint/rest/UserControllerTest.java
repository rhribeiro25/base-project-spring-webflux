package br.com.rhribeiro25.baseprojectspringwebflux.entrypoint.rest;

import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.bpswf.request.UserRequestCreator;
import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.bpswf.request.UserRequestPatch;
import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.bpswf.request.UserRequestPost;
import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.bpswf.request.UserRequestPut;
import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.bpswf.response.UserResponse;
import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.bpswf.response.UserResponseCreator;
import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.generic.response.ObjectResponse;
import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.generic.response.ObjectResponseCreator;
import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.generic.response.PaginatorResponse;
import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.generic.response.PaginatorResponseCreator;
import br.com.rhribeiro25.baseprojectspringwebflux.core.entity.UserEntity;
import br.com.rhribeiro25.baseprojectspringwebflux.core.entity.UserEntityCreator;
import br.com.rhribeiro25.baseprojectspringwebflux.core.useCases.UserServiceImpl;
import br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.adapter.generic.GenericConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Locale;

@ExtendWith(SpringExtension.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private GenericConverter genericConverter;

    @Mock
    private MessageSource messageSource;

    private final UserResponse userResponse = UserResponseCreator.createUserResponse();
    private final ObjectResponse objectResponse = ObjectResponseCreator.createObjectResponse(userResponse, HttpStatus.OK);
    private final ObjectResponse objectResponseCreated = ObjectResponseCreator.createObjectResponse(userResponse, HttpStatus.CREATED);
    private final ObjectResponse objectResponseDeleted = ObjectResponseCreator.createObjectResponse("Usuário(a) deletado(a) com sucesso!", HttpStatus.CREATED);
    private final UserEntity userEntity = UserEntityCreator.createUserEntity();
    private final List<UserResponse> userResponseList = UserResponseCreator.createUserResponseList();
    private final PaginatorResponse paginatorResponse = PaginatorResponseCreator.createPaginatorResponse(userResponseList);
    private final UserRequestPatch userRequestPatch = UserRequestCreator.createUserRequestPatch();
    private final UserRequestPost userRequestPost = UserRequestCreator.createUserRequestPost();
    private final UserRequestPut userRequestPut = UserRequestCreator.createUserRequestPut();
    private final PageRequest pageRequest = PageRequest.of(0, 2);


    @BeforeEach
    public void setUp() {

        genericConverter.modelMapper = new ModelMapper();

        // findById
        BDDMockito.when(userService.findById(1L))
                .thenReturn(Mono.just(userEntity));
        BDDMockito.when(genericConverter.converterMonoToObjectResponse(userEntity, HttpStatus.OK))
                .thenReturn(Mono.just(objectResponse));

        // findAll
        BDDMockito.when(userService.findAll(pageRequest))
                .thenReturn(Flux.fromIterable(userResponseList));
        BDDMockito.when(userService.countByIsActivated(true))
                .thenReturn(Mono.just(2L));
        BDDMockito.when(genericConverter.converterFluxToPaginatorResponse(userService.findAll(pageRequest), pageRequest, userService.countByIsActivated(true)))
                .thenReturn(Mono.just(paginatorResponse));

        // save
        BDDMockito.when(userService.save(userRequestPost))
                .thenReturn(Mono.just(userResponse));
        BDDMockito.when(genericConverter.converterMonoToObjectResponse(userResponse, HttpStatus.CREATED))
                .thenReturn(Mono.just(objectResponseCreated));

        // updateByPut
        BDDMockito.when(userService.updateByPut(userRequestPut))
                .thenReturn(Mono.just(userResponse));
        BDDMockito.when(genericConverter.converterMonoToObjectResponse(userResponse, HttpStatus.OK))
                .thenReturn(Mono.just(objectResponse));

        // updateByPatch
        BDDMockito.when(userService.updateByPatch(1L, userRequestPatch))
                .thenReturn(Mono.just(userResponse));
        BDDMockito.when(genericConverter.converterMonoToObjectResponse(userResponse, HttpStatus.OK))
                .thenReturn(Mono.just(objectResponse));

        // delete
        BDDMockito.when(userService.delete(1L))
                .thenReturn(Mono.just(userResponse));
        BDDMockito.when(genericConverter.converterMonoToObjectResponse(messageSource.getMessage("message.user.deleted.successfully", null, Locale.getDefault()), HttpStatus.OK))
                .thenReturn(Mono.just(objectResponseDeleted));
    }

    @Test
    @DisplayName("find User By Id -> Success With Result")
    public void findByIdUserSucessWithResult() {
        Mono result = userController.findById(1l);
        StepVerifier.create(result)
                .expectSubscription()
                .expectNext(objectResponse)
                .verifyComplete();
    }

    @Test
    @DisplayName("find All User  -> Success With Result")
    public void findAllUserSucessWithResult() {
        Mono result = userController.findAll(0, 2);
        StepVerifier.create(result)
                .expectSubscription()
                .expectNext(paginatorResponse)
                .verifyComplete();
    }

    @Test
    @DisplayName("create User  -> Success With Result")
    public void createUserSucessWithResult() {
        Mono result = userController.save(userRequestPost);
        StepVerifier.create(result)
                .expectSubscription()
                .expectNext(objectResponseCreated)
                .verifyComplete();
    }

    @Test
    @DisplayName("update all fields of User -> Success With Result")
    public void updateUserByPutSucessWithResult() {
        Mono result = userController.updateByPut(userRequestPut);
        StepVerifier.create(result)
                .expectSubscription()
                .expectNext(objectResponse)
                .verifyComplete();
    }

    @Test
    @DisplayName("update specific field of User -> Success With Result")
    public void updateUserByPatchSucessWithResult() {
        Mono result = userController.updateByPatch(1L, userRequestPatch);
        StepVerifier.create(result)
                .expectSubscription()
                .expectNext(objectResponse)
                .verifyComplete();
    }

    @Test
    @DisplayName("delete User -> Success With Result")
    public void deleteUserSucessWithResult() {
        Mono result = userController.delete(1l);
        StepVerifier.create(result)
                .expectSubscription()
                .expectNext(objectResponseDeleted)
                .verifyComplete();
    }
}
