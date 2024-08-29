package br.com.lett.comifood.service;

import br.com.lett.comifood.model.EnterpriseEntity;
import br.com.lett.comifood.record.TokenRecord;
import br.com.lett.comifood.repository.EnterpriseRepository;
import br.com.lett.comifood.rest.TokenRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class TokenService  {

    @Autowired
    private TokenRest tokenRest;

    @Autowired
    private EnterpriseRepository enterpriseRepository;

    public void updateTokens(){

        List<EnterpriseEntity> enterprises = enterpriseRepository.findByStatus("ACTIVE");

        for (EnterpriseEntity enterprise : enterprises) {

            if(isUpdate(enterprise)) {

                TokenRecord tokenRecord = tokenRest.getToken(enterprise);

                if (Objects.nonNull(tokenRecord)) {
                    enterprise.setToken(tokenRecord.accessToken());
                    enterprise.setExpireIn(tokenRecord.expiresIn());
                    LocalDateTime dateTimeExpiredToken = LocalDateTime.now().plusMinutes(tokenRecord.expiresIn() / 60);
                    enterprise.setDateTimeExpiredToken(dateTimeExpiredToken);
                }
                enterpriseRepository.saveAndFlush(enterprise);
            }
        }
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
