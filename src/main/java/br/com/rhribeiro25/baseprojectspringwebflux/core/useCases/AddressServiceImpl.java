package br.com.rhribeiro25.baseprojectspringwebflux.core.useCases;

import br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.adapter.generic.GenericConverter;
import br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.apis.viacep.AddressWebClient;
import br.com.rhribeiro25.baseprojectspringwebflux.error.exception.NotFoundErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Locale;

/**
 * Class Address Service
 *
 * @author Renan Henrique Ribeiro
 * @since 06/07/2021
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressWebClient viaCepWebClient;

    private final MessageSource messageSource;

    public Mono findAddressByZipcode(String cep) {
        return GenericConverter.converterMonoToObjectResponse(viaCepWebClient.findAddressByZipcode(cep), HttpStatus.OK)
                .switchIfEmpty(Mono.error(new NotFoundErrorException(messageSource.getMessage("message.not.found.error.address", null, Locale.getDefault()))));
    }
}
