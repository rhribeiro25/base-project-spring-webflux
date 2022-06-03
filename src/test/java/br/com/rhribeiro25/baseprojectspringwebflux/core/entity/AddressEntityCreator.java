package br.com.rhribeiro25.baseprojectspringwebflux.core.entity;

public class AddressEntityCreator {
    public static AddressEntity createAddressEntity() {
        return AddressEntity.builder()
                .id(1L)
                .street("Rua Alvaro Neto")
                .num("500")
                .district("Pinheiros")
                .complement("")
                .city("São Paulo")
                .zipCode("014515-055")
                .build();
    }


}