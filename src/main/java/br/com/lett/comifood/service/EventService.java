package br.com.lett.comifood.service;

import br.com.lett.comifood.model.EnterpriseEntity;
import br.com.lett.comifood.model.EventEntity;
import br.com.lett.comifood.record.EventRecord;
import br.com.lett.comifood.repository.EnterpriseRepository;
import br.com.lett.comifood.rest.EventRest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EnterpriseRepository enterpriseRepository;

    @Autowired
    private EventRest eventRest;

    public void polling(){

        ModelMapper mapper = new ModelMapper();
        List<EnterpriseEntity> enterprises = enterpriseRepository.findByStatus("ACTIVE");

        for (EnterpriseEntity enterprise : enterprises) {
            List<EventRecord> eventRecords = eventRest.polling(enterprise);
            List<EventEntity> eventEntities = new ArrayList<>();
            mapper.map(eventRecords, eventEntities);

        }
    }


}
