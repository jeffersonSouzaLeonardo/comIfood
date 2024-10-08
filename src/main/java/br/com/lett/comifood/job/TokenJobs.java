package br.com.lett.comifood.job;

import br.com.lett.comifood.apicom.TokenApiCom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class TokenJobs {

    @Autowired
    TokenApiCom tokenApiCom;


    @Scheduled(fixedRate = 300000) // 5 minutes
    private void update(){
        tokenApiCom.updateTokens();
    }

}
