package br.com.lett.comifood.job;

import br.com.lett.comifood.apicom.EventApiCom;
import br.com.lett.comifood.rest.AknowledgmentRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class EventJobs {

    @Autowired
    EventApiCom eventApiCom;

    @Scheduled(fixedRate = 30000) // 30 seconds
    private void polling(){
        eventApiCom.polling();

    }

}
