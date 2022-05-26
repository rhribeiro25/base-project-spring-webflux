package br.com.rhribeiro25.baseprojectspringwebflux.core.entity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class UserEntityCreator {
    public static UserEntity createUserEntity() {
        return UserEntity.builder()
                .id(1L)
                .isActivated(true)
                .firstName("Jeferson")
                .middleName("Oliveira")
                .lastName("Neto")
                .motherName("Maria de Lourdes Oliveira")
                .email("email@gmail.com.br")
                .password("1234%sS")
                .phone("11999999999")
                .role("ADMIN")
                .createdAt(LocalDateTime.of(2022, 03, 11, 9, 30, 0))
                .updatedAt(LocalDateTime.of(2022, 03, 11, 9, 30, 0))
                .build();
    }

    public static UserEntity createUserEntityDeleted() {
        return UserEntity.builder()
                .id(1L)
                .isActivated(false)
                .firstName("Jeferson")
                .middleName("Oliveira")
                .lastName("Neto")
                .motherName("Maria de Lourdes Oliveira")
                .email("email@gmail.com.br")
                .password("1234%sS")
                .phone("11999999999")
                .role("ADMIN")
                .createdAt(LocalDateTime.of(2022, 03, 11, 9, 30, 0))
                .updatedAt(LocalDateTime.of(2022, 03, 11, 9, 30, 0))
                .build();
    }

    public static List<UserEntity> createUserEntityList() {
        return Arrays.asList(
                UserEntity.builder()
                        .id(2L)
                        .isActivated(true)
                        .firstName("Alisson")
                        .middleName("Pereira")
                        .lastName("Mark")
                        .motherName("Fernanda Pereira Mark")
                        .email("email@gmail.com.br")
                        .password("1234%sS")
                        .phone("22999999999")
                        .role("ADMIN")
                        .createdAt(LocalDateTime.of(2022, 03, 12, 9, 30, 0))
                        .updatedAt(LocalDateTime.of(2022, 03, 12, 9, 30, 0))
                        .build(),
                UserEntity.builder()
                        .id(3L)
                        .isActivated(true)
                        .firstName("Jairon")
                        .middleName("Oliveira")
                        .lastName("Neto")
                        .motherName("Maria de Lourdes Oliveira")
                        .email("email@gmail.com.br")
                        .password("4321%sS")
                        .phone("33999999999")
                        .role("USER")
                        .createdAt(LocalDateTime.of(2022, 03, 13, 9, 30, 0))
                        .updatedAt(LocalDateTime.of(2022, 03, 13, 9, 30, 0))
                        .build()
        );
    }

}