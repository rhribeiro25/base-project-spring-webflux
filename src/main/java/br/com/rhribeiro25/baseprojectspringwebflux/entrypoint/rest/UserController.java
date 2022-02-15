package br.com.rhribeiro25.baseprojectspringwebflux.entrypoint.rest;

import br.com.rhribeiro25.baseprojectspringwebflux.config.security.JwtUtil;
import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.bpswf.request.UserRequestPatch;
import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.bpswf.request.UserRequestPost;
import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.bpswf.request.UserRequestPut;
import br.com.rhribeiro25.baseprojectspringwebflux.core.entity.UserEntity;
import br.com.rhribeiro25.baseprojectspringwebflux.core.useCases.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
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
@Log4j2
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public Mono login(@RequestBody UserEntity user) {
        return Mono.just(jwtUtil.generateToken(user));
    }

    @GetMapping(path = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono findAll(@RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size) {
        return userService.findAll(PageRequest.of(page, size));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Mono save(@RequestBody @Valid UserRequestPost user) {
        return userService.save(user);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono update(@RequestBody @Valid UserRequestPut user) {
        return userService.updateByPut(user);
    }

    @PatchMapping(path = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono update(@PathVariable Long id,
                       @RequestBody @Valid UserRequestPatch user) {
        return userService.updateByPatch(id, user);
    }

    @DeleteMapping(path = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono delete(@PathVariable Long id) {
        return userService.delete(id);
    }
}
