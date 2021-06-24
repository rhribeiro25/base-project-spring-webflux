package br.com.rhribeiro25.baseprojectspringwebflux.entrypoint.rest;

import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.bpswf.request.UserCreateRequest;
import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.bpswf.request.UserUpdateRequest;
import br.com.rhribeiro25.baseprojectspringwebflux.core.useCases.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

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
    public Mono findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Mono findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return userService.findAll(PageRequest.of(page, size));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Mono save(@RequestBody @Valid UserCreateRequest user) {
        return userService.save(user);
    }

    @PutMapping(path = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono update(@PathVariable Long id, @RequestBody @Valid UserUpdateRequest user) {
        return userService.update(id, user);
    }
}
