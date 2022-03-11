package br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.bpswf.request;

import br.com.rhribeiro25.baseprojectspringwebflux.core.document.AuthDocument;

public class AuthRequestCreator {

    public static UserRequestLogin createUserRequestLogin() {
        return UserRequestLogin.builder()
                .email("email@dasa.com.br")
                .password("1234%sS")
                .build();
    }

    public static AuthDocument createAuthDocument() {
        return AuthDocument.builder()
                .token("token")
                .build();
    }

}