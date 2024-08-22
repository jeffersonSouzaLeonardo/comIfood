package br.com.lett.comifood.job;

import br.com.lett.comifood.Coments;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;

@Configuration
@EnableScheduling
public class JobOrdered {

    @Scheduled(fixedRate = 10000)
    public void getOrdered(){
        WebClient webClient = WebClient.create();
        var  resps = webClient.get()
                .uri("https://json-placeholder.mock.beeceptor.com/comments")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Coments.class)
                .collectList()
                .block();

        for (Coments rep : resps ) {
            System.out.println(rep.getEmail()+ "  " + LocalDateTime.now());

        }

    }

}
