package br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.apis.viacep;

import br.com.rhribeiro25.baseprojectspringwebflux.config.security.WebSecurityConfig;
import br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.adapter.viacep.AddressConverter;
import br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.apis.viacep.dtos.response.VcAddressResponse;
import br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.apis.viacep.dtos.response.VcAddressResponseCreator;
import br.com.rhribeiro25.baseprojectspringwebflux.utils.ObjectUtilsTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.MediaType;
import org.mockserver.springtest.MockServerTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@MockServerTest("api.viacep.base.url=http://localhost:${mockServerPort}/")
@WebFluxTest(controllers = AddressWebClient.class, excludeAutoConfiguration = {WebSecurityConfig.class})
@AutoConfigureWebTestClient(timeout = "1000")
public class AdressWebClientTest {

    @Autowired
    private AddressWebClient addressWebClient;

    @MockBean
    private AddressConverter addressConverter;

    private MockServerClient mockServer;

    private static final VcAddressResponse vcAddressResponse = VcAddressResponseCreator.createVcAddressResponse();

    @Test
    @DisplayName("Find Address by Zip Code -> Success")
    public void findDoctorsByCouncil() throws JsonProcessingException {
        String responseBody = ObjectUtilsTest.getObjecAsString(vcAddressResponse);
        mockServer
                .when(request()
                        .withMethod(HttpMethod.GET.name()))
                .respond(response()
                        .withStatusCode(HttpStatus.OK.value())
                        .withContentType(MediaType.APPLICATION_JSON_UTF_8)
                        .withBody(responseBody)
                );
        Mono result = addressWebClient.findAddressByZipCode("014515-055");
        StepVerifier.create(result.hide())
                .expectNext(vcAddressResponse)
                .expectComplete()
                .verify();
    }

}

