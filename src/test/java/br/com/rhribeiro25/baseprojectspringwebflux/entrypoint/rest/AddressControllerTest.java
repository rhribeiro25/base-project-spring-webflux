package br.com.rhribeiro25.baseprojectspringwebflux.entrypoint.rest;

import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.generic.response.ObjectResponse;
import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.generic.response.ObjectResponseCreator;
import br.com.rhribeiro25.baseprojectspringwebflux.core.entity.AddressEntity;
import br.com.rhribeiro25.baseprojectspringwebflux.core.entity.AddressEntityCreator;
import br.com.rhribeiro25.baseprojectspringwebflux.core.useCases.AddressServiceImpl;
import br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.adapter.generic.GenericConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
public class AddressControllerTest {

    @InjectMocks
    private AddressController addressController;

    @Mock
    private AddressServiceImpl addressService;

    @Mock
    private GenericConverter genericConverter;

    private final AddressEntity address = AddressEntityCreator.createAddressEntity();

    private final ObjectResponse objectResponse = ObjectResponseCreator.createObjectResponse(address, HttpStatus.OK);

    @BeforeEach
    public void setUp() {

        BDDMockito.when(addressService.findAddressByZipCode("014515-055"))
                .thenReturn(Mono.just(address));
        BDDMockito.when(genericConverter.converterMonoToObjectResponse(address, HttpStatus.OK))
                .thenReturn(Mono.just(objectResponse));
    }

    @Test
    @DisplayName("find Address by cep -> Success With Result")
    public void findAddressByCepSucessWithResult() {
        Mono result = addressController.findAddressByZipCode("014515-055");
        StepVerifier.create(result)
                .expectSubscription()
                .expectNext(objectResponse)
                .verifyComplete();
    }

}
