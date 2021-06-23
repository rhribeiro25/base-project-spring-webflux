package br.com.rhribeiro25.baseprojectspringwebflux.core.useCases;

import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.bpswf.request.UserCreateRequest;
import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.bpswf.request.UserUpdateRequest;
import br.com.rhribeiro25.baseprojectspringwebflux.core.entity.UserEntity;
import br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.adapter.generic.GenericConverter;
import br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.database.postgresql.UserRepository;
import br.com.rhribeiro25.baseprojectspringwebflux.error.exception.BadRequestErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Class User Service
 *
 * @author Renan Henrique Ribeiro
 * @since 06/07/2021
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Mono findById(Long id) {
        return GenericConverter.converterMonoToObjectResponse(userRepository.findById(id), HttpStatus.OK);
    }

    public Mono findAll(Pageable page) {
        Flux<UserEntity> users = userRepository.findAllByIdNotNullOrderByIdAsc(page);
        Mono<Long> count = userRepository.count();
        return GenericConverter.converterFluxToPaginatorResponse(users, page, count);
    }

    public Mono save(UserCreateRequest createdUser) {
        UserEntity user = GenericConverter.converterObjectToObject(createdUser, UserEntity.class);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return GenericConverter.converterMonoToObjectResponse(userRepository.save(user), HttpStatus.CREATED);
    }

    public Mono update(Long id, UserUpdateRequest updatedUser) {
        return userRepository.findById(id).flatMap(oldUser -> {
            if (!oldUser.getEmail().equals(updatedUser.getEmail()))
                return Mono.error(new BadRequestErrorException("O E-Mail não pode ser alterado!"));
            updatedUser.setId(id);
            UserEntity user = GenericConverter.converterObjectToObject(updatedUser, UserEntity.class);
            user.setCreatedAt(oldUser.getCreatedAt());
            user.setUpdatedAt(LocalDateTime.now());
            return GenericConverter.converterMonoToObjectResponse(userRepository.save(user), HttpStatus.OK);
        }).switchIfEmpty(Mono.error(new BadRequestErrorException("O usuário de ID " + id + " não está cadastrado!")));
    }
}
