package br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.adapter.bpswf;

import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.bpswf.request.UserCreateRequest;
import br.com.rhribeiro25.baseprojectspringwebflux.core.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * Class User Converter
 *
 * @author Renan Henrique Ribeiro
 * @since 06/07/2021
 */
@Slf4j
public abstract class UserConverter {

    public final static UserEntity converterUserCreateRequestToUserEntity(UserCreateRequest userRequest) {
        return UserEntity.builder()
                .firstName(userRequest.getFirstName())
                .middleName(userRequest.getMiddleName())
                .lastName(userRequest.getLastName())
                .motherName((userRequest.getMotherName()))
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .phone(userRequest.getPhone())
                .role(userRequest.getRole())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
