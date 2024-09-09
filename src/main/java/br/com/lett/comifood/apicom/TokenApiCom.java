package br.com.lett.comifood.apicom;

import br.com.lett.comifood.enuns.EnterpriseStatus;
import br.com.lett.comifood.model.EnterpriseEntity;
import br.com.lett.comifood.record.TokenRecord;
import br.com.lett.comifood.rest.TokenRest;
import br.com.lett.comifood.service.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokenApiCom {

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private TokenRest tokenRest;

    public void updateTokens(){

        for (EnterpriseEntity enterprise : enterpriseService.findByStatus(EnterpriseStatus.ACTIVE)) {

            if(isUpdate(enterprise)) {
                TokenRecord tokenRecord = tokenRest.getToken(enterprise);
                enterpriseService.saveTokenInEnterprise(enterprise, tokenRecord);
            }
        }
    }

    public void updateSingle(EnterpriseEntity enterprise){
        TokenRecord tokenRecord = tokenRest.getToken(enterprise);
        enterpriseService.saveTokenInEnterprise(enterprise, tokenRecord);
    }

    private boolean isUpdate(EnterpriseEntity enterprise){
        if((enterprise.getDateTimeExpiredToken() == null) ||
                (enterprise.getExpireIn() == null) ||
                (enterprise.getToken() == null)){

            return true;
        }

        if (LocalDateTime.now().isAfter(enterprise.getDateTimeExpiredToken())) {
            return true;
        }

        return false;
    }


}
