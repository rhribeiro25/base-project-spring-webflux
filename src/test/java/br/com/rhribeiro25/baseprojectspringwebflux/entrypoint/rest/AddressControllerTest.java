package br.com.rhribeiro25.baseprojectspringwebflux.entrypoint.rest;

import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.generic.response.ObjectResponse;
import br.com.rhribeiro25.baseprojectspringwebflux.core.useCases.AddressService;
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

@ExtendWith(SpringExtension.class)
public class AddressControllerTest {

    @InjectMocks
    private AddressController addressController;

    @Mock
    private AddressService addressService;

    private final Object objectResponse = ObjectResponse.builder().build();

    @BeforeEach
    public void setUp() {

        BDDMockito.mock(AddressController.class);

        BDDMockito.given(addressService.findAddressByZipcode("37500-000"))
                .willReturn(Mono.just(ObjectResponse.builder()
                        .data(objectResponse)
                        .statusCode(200)
                        .build()));
    }

    @Test
    @DisplayName("find Address by cep -> Success With Result")
    public void findAddressByCepSucessWithResult() {
        Mono result = addressController.findAddressByZipCode("37500-000");
        StepVerifier.create(result)
                .expectSubscription()
                .expectNext(ObjectResponse.builder()
                        .data(objectResponse)
                        .statusCode(200)
                        .build())
                .verifyComplete();
    }

}
