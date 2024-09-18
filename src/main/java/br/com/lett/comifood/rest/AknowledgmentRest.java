package br.com.lett.comifood.rest;

import br.com.lett.comifood.record.AknowledgmentRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
public class AknowledgmentRest {

    private final String URL = "https://merchant-api.ifood.com.br";
    private final String URI = "/events/v1.0/events/acknowledgment";

    public void postAknowledgment(List<AknowledgmentRecord> aknowledgmentRecordsList){

        WebClient webClient = WebClient.builder()
                .baseUrl(URL)
                .build();

        Mono<ClientResponse> responseMono = webClient.post()
                .uri(URI)
                .header("Authorization", "Bearer " + aknowledgmentRecordsList.get(0).token())
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(aknowledgmentRecordsList), List.class)
                .retrieve()
                .bodyToMono(ClientResponse.class);

        if (responseMono != null) {
            int statusCode = responseMono
                    .map(ClientResponse::statusCode)
                    .block().value();

            log.info(" Envio do Aknowledgment, Status code: " + statusCode);
        }

    }
}
