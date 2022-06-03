package br.com.rhribeiro25.baseprojectspringwebflux.entrypoint.rest;

import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.bpswf.request.UserRequestPatch;
import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.bpswf.request.UserRequestPost;
import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.bpswf.request.UserRequestPut;
import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.bpswf.response.UserResponse;
import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.generic.response.ObjectResponse;
import br.com.rhribeiro25.baseprojectspringwebflux.core.useCases.UserService;
import br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.adapter.generic.GenericConverter;
import org.springframework.context.MessageSource;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.Locale;

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
    private GenericConverter genericConverter;

    @Autowired
    private MessageSource messageSource;

    @GetMapping(path = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ObjectResponse> findById(@PathVariable Long id) {
        return userService.findById(id).flatMap(user -> genericConverter.converterMonoToObjectResponse(user, HttpStatus.OK));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Flux userResponseList = userService.findAll(pageRequest);
        Mono<Long> count = userService.countByIsActivated(true);
        return genericConverter.converterFluxToPaginatorResponse(userResponseList, pageRequest, count);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Mono save(@RequestBody @Valid UserRequestPost user) {
        Mono<UserResponse> userResponse = userService.save(user);
        return userResponse.flatMap(userResp -> genericConverter.converterMonoToObjectResponse(userResp, HttpStatus.CREATED));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono updateByPut(@RequestBody @Valid UserRequestPut user) {
        Mono<UserResponse> userResponse = userService.updateByPut(user);
        return userResponse.flatMap(userResp -> genericConverter.converterMonoToObjectResponse(userResp, HttpStatus.OK));
    }

    @PatchMapping(path = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono updateByPatch(@PathVariable Long id, @RequestBody @Valid UserRequestPatch user) {
        Mono<UserResponse> userResponse = userService.updateByPatch(id, user);
        return userResponse.flatMap(userResp -> genericConverter.converterMonoToObjectResponse(userResp, HttpStatus.OK));
    }

    @DeleteMapping(path = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono delete(@PathVariable Long id) {
        return userService.delete(id).flatMap(resp -> genericConverter.converterMonoToObjectResponse(messageSource.getMessage("message.user.deleted.successfully", null, Locale.getDefault()), HttpStatus.OK));
    }
}
