package br.com.rhribeiro25.baseprojectspringwebflux.core.useCases;

import reactor.core.publisher.Mono;

/**
 * Interface Address Service
 *
 * @author Renan Henrique Ribeiro
 * @since 06/27/2021
 */
public interface AddressService {

    Mono findAddressByZipCode(String cep);
}
