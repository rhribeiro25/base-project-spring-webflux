package br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.viacep.response;

public class AddressResponseCreator {
    public static AddressResponse createAddressResponse() {
        return AddressResponse.builder()
                .zipCode("014515-055")
                .street("Rua Alvaro Neto")
                .complement("500")
                .district("Pinheiros")
                .city("São Paulo")
                .build();
    }


}