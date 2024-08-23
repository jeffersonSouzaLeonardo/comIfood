package br.com.lett.comifood.service;

import br.com.lett.comifood.model.EnterpriseEntity;
import br.com.lett.comifood.repository.EnterpriseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenService  {

    @Autowired
    private EnterpriseRepository enterpriseRepository;

    public void updateTokens(){

        List<EnterpriseEntity> enterprises = enterpriseRepository.findByStatus("ACTIVE");

        for (EnterpriseEntity enterprise : enterprises) {
            System.out.println(enterprise.getEnterprise() + " - " + enterprise.getStatus() );
        }
    }

}
