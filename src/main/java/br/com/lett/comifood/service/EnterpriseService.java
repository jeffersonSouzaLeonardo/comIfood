package br.com.lett.comifood.service;

import br.com.lett.comifood.enuns.EnterpriseStatus;
import br.com.lett.comifood.model.EnterpriseEntity;
import br.com.lett.comifood.record.TokenRecord;
import br.com.lett.comifood.repository.EnterpriseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class EnterpriseService {

    @Autowired
    EnterpriseRepository enterpriseRepository;

    public List<EnterpriseEntity> findByStatus(EnterpriseStatus status){
        return enterpriseRepository.findByStatus(status);
    }

    public void saveTokenInEnterprise(EnterpriseEntity enterprise, TokenRecord tokenRecord){
        if (Objects.nonNull(tokenRecord)) {
            enterprise.setToken(tokenRecord.accessToken());
            enterprise.setExpireIn(tokenRecord.expiresIn());
            LocalDateTime dateTimeExpiredToken = LocalDateTime.now().plusMinutes(tokenRecord.expiresIn() / 60);
            enterprise.setDateTimeExpiredToken(dateTimeExpiredToken);
            save(enterprise);
        }
    }

    public EnterpriseEntity save(EnterpriseEntity enterprise){
        try {

            enterprise = enterpriseRepository.saveAndFlush(enterprise);

        } catch ( Exception e ) {
            log.error( "Erro ao salvar  o token na Enterprise. Enterprise: " + enterprise.getEnterprise() + " - " + e.getMessage() );
        }

        return enterprise;
    }

}
