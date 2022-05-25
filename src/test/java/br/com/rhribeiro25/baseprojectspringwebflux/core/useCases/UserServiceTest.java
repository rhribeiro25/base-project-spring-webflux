package br.com.rhribeiro25.baseprojectspringwebflux.core.useCases;

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
import br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.adapter.generic.GenericConverter;
import br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.database.postgresql.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient(timeout = "20000")
class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private GenericConverter genericConverter;

    @Mock
    private MessageSource messageSource;

    @Mock
    private UserRepository userRepository;

    private final UserEntity userEntity = UserEntityCreator.createUserEntity();
    private final UserResponse userResponse = UserResponseCreator.createUserResponse();
    private final ObjectResponse objectResponse = ObjectResponseCreator.createObjectResponse(userResponse, HttpStatus.OK);
    private final List<UserEntity> userEntityList = UserEntityCreator.createUserEntityList();
    private final List<UserResponse> userResponseList = UserResponseCreator.createUserResponseList();
    private final PaginatorResponse paginatorResponse = PaginatorResponseCreator.createPaginatorResponse(userEntityList);
    private final UserRequestPatch userRequestPatch = UserRequestCreator.createUserRequestPatch();
    private final UserRequestPost userRequestPost = UserRequestCreator.createUserRequestPost();
    private final UserRequestPut userRequestPut = UserRequestCreator.createUserRequestPut();
    private final PageRequest pageRequest = PageRequest.of(0, 2);


    @BeforeEach
    public void setUp() {

        genericConverter.modelMapper = new ModelMapper();

        BDDMockito.when(genericConverter.converterObjectToObject(userEntity, UserResponse.class))
                .thenReturn(userResponse);
        BDDMockito.when(userRepository.findByIdAndIsActivated(1L, true))
                .thenReturn(Mono.just(userEntity));

        for (int i = 0; i < userEntityList.size(); i++) {
            BDDMockito.when(genericConverter.converterObjectToObject(userEntityList.get(i), UserResponse.class))
                    .thenReturn(userResponseList.get(i));
        }
        BDDMockito.when(userRepository.findAllByIsActivated(pageRequest, true))
                .thenReturn(Flux.fromIterable(userEntityList));


        BDDMockito.when(userRepository.save(userEntity))
                .thenReturn(Mono.just(userEntity));

    }

    @Test
    @DisplayName("find User By Id -> Success With Result")
    public void findByIdUserSucessWithResult() {
        Mono result = userService.findById(1l);
        StepVerifier.create(result)
                .expectSubscription()
                .expectNext(userResponse)
                .verifyComplete();
    }

    @Test
    @DisplayName("find All User  -> Success With Result")
    public void findAllUserSucessWithResult() {
        Flux result = userService.findAll(pageRequest);
        StepVerifier.create(result)
                .expectSubscription()
                .expectNextSequence(userResponseList)
                .verifyComplete();
    }


    @Test
    @DisplayName("create User  -> Success With Result")
    public void createUserSucessWithResult() {
        Mono result = userService.save(userRequestPost);
        StepVerifier.create(result)
                .expectSubscription()
                .expectNext(ObjectResponse.builder()
                        .data(objectResponse)
                        .statusCode(201)
                        .build())
                .verifyComplete();
    }
//
//    @Test
//    @DisplayName("update all fields of User -> Success With Result")
//    public void updateUserByPutSucessWithResult() {
//        Mono result = userController.updateByPut(userRequestPut);
//        StepVerifier.create(result)
//                .expectSubscription()
//                .expectNext(ObjectResponse.builder()
//                        .data(objectResponse)
//                        .statusCode(200)
//                        .build())
//                .verifyComplete();
//    }
//
//    @Test
//    @DisplayName("update specific field of User -> Success With Result")
//    public void updateUserByPatchSucessWithResult() {
//        Mono result = userController.updateByPatch(1L, userRequestPatch);
//        StepVerifier.create(result)
//                .expectSubscription()
//                .expectNext(ObjectResponse.builder()
//                        .data(objectResponse)
//                        .statusCode(200)
//                        .build())
//                .verifyComplete();
//    }
//
//    @Test
//    @DisplayName("delete User -> Success With Result")
//    public void deleteUserSucessWithResult() {
//        Mono result = userController.delete(1l);
//
//        StepVerifier.create(result)
//                .expectSubscription()
//                .expectNext(ObjectResponse.builder()
//                        .data("Usuário deletado com sucesso!")
//                        .statusCode(200)
//                        .build())
//                .verifyComplete();
//    }
}
