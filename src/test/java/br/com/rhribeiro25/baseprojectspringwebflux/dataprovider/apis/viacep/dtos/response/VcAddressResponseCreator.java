package br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.apis.viacep.dtos.response;

public class VcAddressResponseCreator {
    public static VcAddressResponse createVcAddressResponse() {
        return VcAddressResponse.builder()
                .cep("014515-055")
                .logradouro("Rua Alvaro Neto")
                .complemento("500")
                .bairro("Pinheiros")
                .localidade("São Paulo")
                .build();
    }


}