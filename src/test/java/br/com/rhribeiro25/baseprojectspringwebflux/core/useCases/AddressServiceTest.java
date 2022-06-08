package br.com.rhribeiro25.baseprojectspringwebflux.core.useCases;

import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.viacep.response.AddressResponse;
import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.viacep.response.AddressResponseCreator;
import br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.adapter.viacep.AddressConverter;
import br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.apis.viacep.AddressWebClient;
import br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.apis.viacep.dtos.response.VcAddressResponse;
import br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.apis.viacep.dtos.response.VcAddressResponseCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
public class AddressServiceTest {

    @InjectMocks
    private AddressServiceImpl addressService;

    @Mock
    private AddressWebClient addressWebClient;

    @Mock
    private AddressConverter addressConverter;

    @Mock
    private MessageSource messageSource;

    private final VcAddressResponse vcAddressResponse = VcAddressResponseCreator.createVcAddressResponse();
    private static final AddressResponse addressResponse = AddressResponseCreator.createAddressResponse();

    @BeforeEach
    public void setUp() {

        BDDMockito.when(addressConverter.converterVcAddressResponseToAddressResponse(vcAddressResponse))
                .thenReturn(Mono.just(addressResponse));
        BDDMockito.when(addressWebClient.findAddressByZipCode("014515-055"))
                .thenReturn(Mono.just(vcAddressResponse));
    }

    @Test
    @DisplayName("find Address by cep -> Success With Result")
    public void findAddressByCepSucessWithResult() {
        Mono result = addressService.findAddressByZipCode("014515-055");
        StepVerifier.create(result)
                .expectSubscription()
                .expectNext(addressResponse)
                .verifyComplete();
    }

}
