package br.com.rhribeiro25.baseprojectspringwebflux.entrypoint.facade.rest;

import br.com.rhribeiro25.baseprojectspringwebflux.core.useCases.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

    private static final int DELAY_PER_ITEM_MS = 100;

    @GetMapping(path = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping()
    public Mono findAll(final @RequestParam(name = "page") int page,
                        final @RequestParam(name = "size") int size) {
        return userService.findAll(PageRequest.of(page, size));
    }
}
