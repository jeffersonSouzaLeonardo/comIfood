package br.com.lett.comifood.rest;

import br.com.lett.comifood.model.EnterpriseEntity;
import br.com.lett.comifood.record.EventRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
@Slf4j
@Component
public class EventRest {

    private final String URI_EVENTS_POLLING = "https://merchant-api.ifood.com.br/events/v1.0/events:polling";

    public List<EventRecord> polling(EnterpriseEntity enterprise){

        WebClient webClient = WebClient.builder().build();
        Mono<List<EventRecord>> result = webClient.get()
                .uri(URI_EVENTS_POLLING)
                .header("Authorization", "Bearer " + enterprise.getToken())
                .retrieve()

                .onStatus(HttpStatusCode::is5xxServerError,
                        clientResponse -> {
                            log.error("Erro Servidor. Empresa:" + enterprise.getEnterprise());
                            return Mono.error(new RuntimeException("Erro Servidor"));
                        })
                .onStatus(HttpStatusCode::is4xxClientError,
                        clientResponse -> {
                            log.error("Erro autenticação. Empresa:" + enterprise.getEnterprise());
                            updateTokenError();
                            return Mono.error(new RuntimeException("Erro Autenticação"));
                        })
                .onStatus(HttpStatusCode::is3xxRedirection,
                        clientResponse -> {
                            log.error("Erro redirecionamento. Empresa:" + enterprise.getEnterprise());
                            return Mono.error(new RuntimeException("Erro de Redirecionamento"));
                        })

                .bodyToFlux(EventRecord.class)
                .collectList();

        return result.block();
    }

    private void updateTokenError(){

    }

}
