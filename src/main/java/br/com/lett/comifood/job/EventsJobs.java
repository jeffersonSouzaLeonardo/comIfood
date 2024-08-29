package br.com.lett.comifood.job;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class EventsJobs {

    @Scheduled(fixedRate = 30000) // 30 seconds
    private void polling(){

    }
}
