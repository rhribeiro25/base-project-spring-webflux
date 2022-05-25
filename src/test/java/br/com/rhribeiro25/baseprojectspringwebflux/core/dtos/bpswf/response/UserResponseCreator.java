package br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.bpswf.response;

import br.com.rhribeiro25.baseprojectspringwebflux.core.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class UserResponseCreator {
    public static UserResponse createUserResponse() {
        return UserResponse.builder()
                .id(1L)
                .firstName("Jeferson")
                .middleName("Oliveira")
                .lastName("Neto")
                .motherName("Maria de Lourdes Oliveira")
                .email("email@gmail.com.br")
                .phone("11999999999")
                .role("ADMIN")
                .createdAt(LocalDateTime.of(2022, 03, 11, 9, 30, 0))
                .updatedAt(LocalDateTime.of(2022, 03, 11, 9, 30, 0))
                .build();
    }

    public static List<UserResponse> createUserResponseList() {
        return Arrays.asList(
                UserResponse.builder()
                        .id(2L)
                        .firstName("Alisson")
                        .middleName("Pereira")
                        .lastName("Mark")
                        .motherName("Fernanda Pereira Mark")
                        .email("email@gmail.com.br")
                        .phone("22999999999")
                        .role("ADMIN")
                        .createdAt(LocalDateTime.of(2022, 03, 12, 9, 30, 0))
                        .updatedAt(LocalDateTime.of(2022, 03, 12, 9, 30, 0))
                        .build(),
                UserResponse.builder()
                        .id(3L)
                        .firstName("Jairon")
                        .middleName("Oliveira")
                        .lastName("Neto")
                        .motherName("Maria de Lourdes Oliveira")
                        .email("email@gmail.com.br")
                        .phone("33999999999")
                        .role("USER")
                        .createdAt(LocalDateTime.of(2022, 03, 13, 9, 30, 0))
                        .updatedAt(LocalDateTime.of(2022, 03, 13, 9, 30, 0))
                        .build()
        );
    }


}