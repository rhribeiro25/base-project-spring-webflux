package br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.apis.viaCep;

import br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.apis.viaCep.dtos.response.AddressResponse;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.util.concurrent.TimeUnit;

@Component
public class AddressWebClient {

    private final WebClient webClient;

    private String API_BASE_URL;

    @Autowired
    public AddressWebClient(Environment env) {
        this.API_BASE_URL = "http://viacep.com.br/";

        HttpClient httpClient = HttpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 120000)
                .doOnConnected(connection -> connection
                        .addHandlerLast(new ReadTimeoutHandler(60000, TimeUnit.MILLISECONDS))
                        .addHandlerLast(new WriteTimeoutHandler(60000, TimeUnit.MILLISECONDS)));
        this.webClient = WebClient.builder()
                .baseUrl(API_BASE_URL)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer
                                .defaultCodecs()
                                .maxInMemorySize(5 * 1024 * 1024))
                        .build())
                .build();
    }

    public Mono findAddressByZipcode(String cep) {
        Mono<AddressResponse> dpExamPreparationsResponse = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("ws/{cep}/json")
                        .build(cep))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> {
                    System.out.println(response);
                    return Mono.empty();
                })
                .onStatus(HttpStatus::is5xxServerError, response -> {
                    System.out.println(response);
                    return Mono.empty();
                })
                .bodyToMono(AddressResponse.class);
        return dpExamPreparationsResponse;
    }
}
