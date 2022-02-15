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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
public class UserServiceImpl implements UserService {

    @Autowired
    MessageSource messageSource;

    private final UserRepository userRepository;

    public Mono findById(Long id) {
        return GenericConverter.converterMonoToObjectResponse(userRepository.findByIdAndIsActivated(id, true), HttpStatus.OK);
    }

    public Mono findByEmail(String email) {
        return GenericConverter.converterMonoToObjectResponse(userRepository.findByEmail(email), HttpStatus.OK).cast(UserDetails.class);
    }

    public Mono findAll(Pageable page) {
        Flux<UserEntity> users = userRepository.findAllByIsActivated(page, true);
        Mono<Long> count = userRepository.count();
        return GenericConverter.converterFluxToPaginatorResponse(users, page, count);
    }

    @Transactional
    public Mono save(Object createdUser) {
        return verifyUserByEmailOrActivateUser((UserRequestPost) createdUser).switchIfEmpty(Mono.defer(() -> {
            UserEntity user = GenericConverter.converterObjectToObject(createdUser, UserEntity.class);
            return GenericConverter.converterMonoToObjectResponse(userRepository.save(user), HttpStatus.CREATED);
        }));
    }

    @Transactional
    public Mono updateByPut(Object updatedUser) {
        return userRepository.findByIdAndIsActivated(((UserRequestPut) updatedUser).getId(), true).flatMap(oldUser -> {
            if (!oldUser.getEmail().equals(((UserRequestPut) updatedUser).getEmail()))
                return Mono.error(new BadRequestErrorException(
                        messageSource.getMessage("message.bad.request.error.email", null, Locale.getDefault()))
                );
            UserEntity user = GenericConverter.converterObjectToObject(updatedUser, UserEntity.class);
            return GenericConverter.converterMonoToObjectResponse(userRepository.save(user), HttpStatus.OK);
        }).switchIfEmpty(Mono.error(new BadRequestErrorException(
                messageSource.getMessage("message.bad.request.error.find.user", null, Locale.getDefault())))
        );
    }

    @Transactional
    public Mono updateByPatch(Long id, Object updatedUser) {
        return setUserData(id, ((UserRequestPatch) updatedUser)).flatMap(oldUser ->
                        GenericConverter.converterMonoToObjectResponse(userRepository.save(oldUser), HttpStatus.OK))
                .switchIfEmpty(Mono.error(new BadRequestErrorException(
                        messageSource.getMessage("message.bad.request.error.find.user", null, Locale.getDefault())))
                );
    }

    @Transactional
    public Mono delete(Long id) {
        return userRepository.findByIdAndIsActivated(id, true).flatMap(oldUser -> {
            oldUser.setIsActivated(false);
            return userRepository.save(oldUser).flatMap(deletedUser ->
                    GenericConverter.converterMonoToObjectResponse(
                            Mono.just(messageSource.getMessage("message.user.deleted.successfully", null, Locale.getDefault())), HttpStatus.OK
                    )
            );
        }).switchIfEmpty(Mono.error(new BadRequestErrorException(messageSource.getMessage("message.bad.request.error.find.user", null, Locale.getDefault()))));
    }

    public Mono verifyUserByEmailOrActivateUser(UserRequestPost createdUser) {
        return userRepository.findByEmail(createdUser.getEmail()).flatMap(existingUser -> {
            if (existingUser.getIsActivated())
                return Mono.error(new BadRequestErrorException(
                        messageSource.getMessage("message.bad.request.error.existing.user", null, Locale.getDefault()))
                );
            return updateByPatch(existingUser.getId(), GenericConverter.converterObjectToObject(createdUser, UserRequestPatch.class));
        });
    }

    public Mono<UserEntity> setUserData(Long id, UserRequestPatch updatedUser) {
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
                    return Mono.error(new BadRequestErrorException(
                            messageSource.getMessage("message.bad.request.error.email", null, Locale.getDefault()))
                    );
                oldUser.setEmail(updatedUser.getEmail());
            }
            if (!StringUtils.isNullOrBlank(updatedUser.getPassword()))
                oldUser.setPassword(updatedUser.getPassword());
            if (!StringUtils.isNullOrBlank(updatedUser.getPhone()))
                oldUser.setPhone(updatedUser.getPhone());
            if (!StringUtils.isNullOrBlank(updatedUser.getRole()))
                oldUser.setRole(updatedUser.getRole());
            oldUser.setIsActivated(true);
            return Mono.just(oldUser);
        });
    }
}
