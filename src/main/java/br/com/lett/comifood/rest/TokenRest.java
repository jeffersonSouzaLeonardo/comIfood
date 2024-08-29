package br.com.lett.comifood.rest;

import br.com.lett.comifood.model.EnterpriseEntity;
import br.com.lett.comifood.record.TokenRecord;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class TokenRest {

    private final String URI_TOKEN = "https://merchant-api.ifood.com.br/authentication/v1.0/oauth/token";

    public TokenRecord getToken(EnterpriseEntity enterpriseEntity){
        WebClient webClient = WebClient.create();

        UriComponentsBuilder builder = UriComponentsBuilder.fromUri(URI.create(URI_TOKEN))
                .queryParam("clientId", enterpriseEntity.getIdClientIfood())
                .queryParam("clientSecret", enterpriseEntity.getClientSecret())
                .queryParam("grantType", "client_credentials");
        String urlWithParams = builder.build().toUriString();

        ResponseEntity<TokenRecord> result = webClient.post()
                .uri(urlWithParams)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .retrieve()
                .toEntity(TokenRecord.class)
                .single()
                .block();

        return result.getBody();

    }
}
