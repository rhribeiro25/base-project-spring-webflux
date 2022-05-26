package br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.bpswf.request;

import java.time.LocalDateTime;

public class UserRequestCreator {

    public static UserRequestPatch createUserRequestPatch() {
        return UserRequestPatch.builder()
                .firstName("Jeferson")
                .middleName("Oliveira")
                .lastName("Neto")
                .motherName("Maria de Lourdes Oliveira")
                .email("email@gmail.com.br")
                .password("1234%sS")
                .phone("11999999999")
                .role("ADMIN")
                .build();
    }

    public static UserRequestPost createUserRequestPost() {
        return UserRequestPost.builder()
                .firstName("Jeferson")
                .middleName("Oliveira")
                .lastName("Neto")
                .motherName("Maria de Lourdes Oliveira")
                .email("email@gmail.com.br")
                .password("1234%sS")
                .phone("11999999999")
                .role("ADMIN")
                .build();
    }

    public static UserRequestPut createUserRequestPut() {
        return UserRequestPut.builder()
                .id(1L)
                .firstName("Jeferson")
                .middleName("Oliveira")
                .lastName("Neto")
                .motherName("Maria de Lourdes Oliveira")
                .email("email@gmail.com.br")
                .password("1234%sS")
                .phone("11999999999")
                .role("ADMIN")
                .createdAt(LocalDateTime.of(2022, 03, 11, 9, 30, 0))
                .updatedAt(LocalDateTime.of(2022, 04, 15, 14, 15, 0))
                .build();
    }


}