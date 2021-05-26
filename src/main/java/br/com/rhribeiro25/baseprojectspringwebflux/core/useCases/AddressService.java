package br.com.rhribeiro25.baseprojectspringwebflux.core.useCases;

import br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.apis.viaCep.AddressWebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AddressService {
    @Autowired
    private AddressWebClient viaCepWebClient;

    public Mono findAddressByZipcode(String cep){
        return viaCepWebClient.findAddressByZipcode(cep);
    }
}
