package br.com.rhribeiro25.baseprojectspringwebflux.entrypoint.rest;

import br.com.rhribeiro25.baseprojectspringwebflux.core.constraints.CepConstraint;
import br.com.rhribeiro25.baseprojectspringwebflux.core.useCases.AddressService;
import br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.adapter.generic.GenericConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * Class Address Controller
 *
 * @author Renan Henrique Ribeiro
 * @since 06/07/2021
 */
@RestController
@RequestMapping("api/addresses")
@Validated
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private GenericConverter genericConverter;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono findAddressByZipCode(@RequestParam @CepConstraint String cep) {
        return addressService.findAddressByZipcode(cep).flatMap(address -> genericConverter.converterMonoToObjectResponse(address, HttpStatus.OK));
    }
}
