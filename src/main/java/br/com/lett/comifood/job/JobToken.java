package br.com.lett.comifood.job;

import br.com.lett.comifood.record.TokenRecord;
import br.com.lett.comifood.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Configuration
@EnableScheduling
public class JobToken {

    @Autowired
    TokenService tokenService;

    @Scheduled(fixedRate = 10000)
    public void getOrdered(){
        WebClient webClient = WebClient.create();

        UriComponentsBuilder builder = UriComponentsBuilder.fromUri(URI.create("https://merchant-api.ifood.com.br/authentication/v1.0/oauth/token"))
                .queryParam("clientId", "--")
                .queryParam("clientSecret", "--")
                .queryParam("grantType", "client_credentials");
        String urlWithParams = builder.build().toUriString();

        var result = webClient.post()
                .uri(urlWithParams)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .retrieve()
                .bodyToFlux(TokenRecord.class)
                .single()
                .block();

        tokenService.updateTokens();

    }

}
