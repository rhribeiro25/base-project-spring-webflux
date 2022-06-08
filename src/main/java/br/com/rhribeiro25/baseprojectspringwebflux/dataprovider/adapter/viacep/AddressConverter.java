package br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.adapter.viacep;

import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.viacep.response.AddressResponse;
import br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.apis.viacep.dtos.response.VcAddressResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Class Address Converter
 *
 * @author Renan Henrique Ribeiro
 * @since 06/07/2021
 */
@Log4j2
@Component
public class AddressConverter {

    public Mono converterVcAddressResponseToAddressResponse(VcAddressResponse vcAddressResponse) {
        return Mono.just(AddressResponse.builder()
                .zipCode(vcAddressResponse.getCep())
                .street(vcAddressResponse.getLogradouro())
                .complement(vcAddressResponse.getComplemento())
                .district(vcAddressResponse.getBairro())
                .city(vcAddressResponse.getLocalidade())
                .build());
    }
}
