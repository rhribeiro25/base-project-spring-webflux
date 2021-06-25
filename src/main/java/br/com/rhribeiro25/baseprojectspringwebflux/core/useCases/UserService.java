package br.com.rhribeiro25.baseprojectspringwebflux.core.useCases;

import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.bpswf.request.UserRequestPatch;
import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.bpswf.request.UserRequestPost;
import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.bpswf.request.UserRequestPut;
import br.com.rhribeiro25.baseprojectspringwebflux.core.entity.UserEntity;
import br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.adapter.generic.GenericConverter;
import br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.database.postgresql.UserRepository;
import br.com.rhribeiro25.baseprojectspringwebflux.error.exception.BadRequestErrorException;
import br.com.rhribeiro25.baseprojectspringwebflux.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Locale;

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

    @Autowired
    MessageSource messageSource;

    private final UserRepository userRepository;

    public Mono findById(Long id) {
        return GenericConverter.converterMonoToObjectResponse(userRepository.findById(id), HttpStatus.OK);
    }

    public Mono findAll(Pageable page) {
        Flux<UserEntity> users = userRepository.findAllByIdNotNullOrderByIdAsc(page);
        Mono<Long> count = userRepository.count();
        return GenericConverter.converterFluxToPaginatorResponse(users, page, count);
    }

    public Mono save(UserRequestPost createdUser) {
        return userRepository.findByEmail(createdUser.getEmail()).flatMap(existingUser ->
                Mono.error(new BadRequestErrorException(messageSource.getMessage("message.bad.request.error.existing.user", null, Locale.getDefault())))
        ).switchIfEmpty(Mono.defer(() -> {
            UserEntity user = GenericConverter.converterObjectToObject(createdUser, UserEntity.class);
            return GenericConverter.converterMonoToObjectResponse(userRepository.save(user), HttpStatus.CREATED);
        }));
    }

    public Mono updateByPut(UserRequestPut updatedUser) {
        return userRepository.findById(updatedUser.getId()).flatMap(oldUser -> {
            if (!oldUser.getEmail().equals(updatedUser.getEmail()))
                return Mono.error(new BadRequestErrorException(messageSource.getMessage("message.bad.request.error.email", null, Locale.getDefault())));
            UserEntity user = GenericConverter.converterObjectToObject(updatedUser, UserEntity.class);
            return GenericConverter.converterMonoToObjectResponse(userRepository.save(user), HttpStatus.OK);
        }).switchIfEmpty(Mono.error(new BadRequestErrorException(messageSource.getMessage("message.bad.request.error.find.user", null, Locale.getDefault()))));
    }

    public Mono updateByPatch(Long id, UserRequestPatch updatedUser) {
        return userRepository.findById(id).flatMap(oldUser -> {
            if (!StringUtils.isNullOrBlank(updatedUser.getFirstName()))
                oldUser.setFirstName(updatedUser.getFirstName());
            if (!StringUtils.isNullOrBlank(updatedUser.getMiddleName()))
                oldUser.setMiddleName(updatedUser.getMiddleName());
            if (!StringUtils.isNullOrBlank(updatedUser.getLastName()))
                oldUser.setLastName(updatedUser.getLastName());
            if (!StringUtils.isNullOrBlank(updatedUser.getMotherName()))
                oldUser.setMotherName(updatedUser.getMotherName());
            if (!StringUtils.isNullOrBlank(updatedUser.getEmail())) {
                if (!oldUser.getEmail().equals(updatedUser.getEmail()))
                    return Mono.error(new BadRequestErrorException(messageSource.getMessage("message.bad.request.error.email", null, Locale.getDefault())));
                oldUser.setEmail(updatedUser.getEmail());
            }
            if (!StringUtils.isNullOrBlank(updatedUser.getPassword()))
                oldUser.setPassword(updatedUser.getPassword());
            if (!StringUtils.isNullOrBlank(updatedUser.getPhone()))
                oldUser.setPhone(updatedUser.getPhone());
            if (!StringUtils.isNullOrBlank(updatedUser.getRole()))
                oldUser.setRole(updatedUser.getRole());
            return GenericConverter.converterMonoToObjectResponse(userRepository.save(oldUser), HttpStatus.OK);
        }).switchIfEmpty(Mono.error(new BadRequestErrorException(messageSource.getMessage("message.bad.request.error.find.user", null, Locale.getDefault()))));
    }
}
