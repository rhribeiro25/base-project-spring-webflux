package br.com.rhribeiro25.baseprojectspringwebflux.core.useCases;

import br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.apis.viacep.AddressWebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Class Address Service
 *
 * @author Renan Henrique Ribeiro
 * @since 06/07/2021
 */
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressWebClient viaCepWebClient;

    public Mono findAddressByZipcode(String cep) {
        return viaCepWebClient.findAddressByZipcode(cep);
    }
}
