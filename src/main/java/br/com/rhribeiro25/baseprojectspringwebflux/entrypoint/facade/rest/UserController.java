package br.com.rhribeiro25.baseprojectspringwebflux.entrypoint.facade.rest;

import br.com.rhribeiro25.baseprojectspringwebflux.core.useCases.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * Class User Controller
 *
 * @author Renan Henrique Ribeiro
 * @since 06/07/2021
 */
@RestController
@RequestMapping("api/users")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono findAddressByZipCode(@PathVariable Long id) {
        return userService.findById(id);
    }
}
