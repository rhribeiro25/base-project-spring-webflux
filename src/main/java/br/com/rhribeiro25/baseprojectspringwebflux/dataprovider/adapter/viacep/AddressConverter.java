package br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.adapter.viacep;

import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.viacep.response.AddressResponse;
import br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.apis.viacep.dtos.response.VcAddressResponse;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * Class Address Converter
 *
 * @author Renan Henrique Ribeiro
 * @since 06/07/2021
 */
@Slf4j
public abstract class AddressConverter {

    public final static Mono converterVcAddressResponseToAddressResponse(Mono<VcAddressResponse> vcAddressResponse) {
        return vcAddressResponse.flatMap(response -> Mono.just(AddressResponse.builder()
                .zipCode(response.getCep())
                .street(response.getLogradouro())
                .complement(response.getComplemento())
                .district(response.getBairro())
                .city(response.getLocalidade())
                .build()));
    }
}
