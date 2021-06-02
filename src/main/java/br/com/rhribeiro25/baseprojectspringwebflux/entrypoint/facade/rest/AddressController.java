package br.com.rhribeiro25.baseprojectspringwebflux.entrypoint.facade.rest;

import br.com.rhribeiro25.baseprojectspringwebflux.core.constrain.CepConstraint;
import br.com.rhribeiro25.baseprojectspringwebflux.core.useCases.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/addresses")
@Validated
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono findAddressByZipCode(@RequestParam @CepConstraint String cep) {
        return addressService.findAddressByZipcode(cep);
    }
}
