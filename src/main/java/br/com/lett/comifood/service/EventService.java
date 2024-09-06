package br.com.lett.comifood.service;

import br.com.lett.comifood.enuns.EnterpriseStatus;
import br.com.lett.comifood.mapper.EventRecordEventEntityMapper;
import br.com.lett.comifood.model.EnterpriseEntity;
import br.com.lett.comifood.model.EventEntity;
import br.com.lett.comifood.record.EventRecord;
import br.com.lett.comifood.repository.EnterpriseRepository;
import br.com.lett.comifood.repository.EventRepository;
import br.com.lett.comifood.rest.EventRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EnterpriseRepository enterpriseRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventRest eventRest;

    @Autowired
    EventRecordEventEntityMapper eventRecordEventEntityMapper;

    public void polling(){

        List<EnterpriseEntity> enterprises = enterpriseRepository.findByStatus(EnterpriseStatus.ACTIVE);

        for (EnterpriseEntity enterprise : enterprises) {

            List<EventRecord> eventRecords = eventRest.polling(enterprise);

            List<EventEntity> eventEntities = new ArrayList<>();
            for (EventRecord eventFrom : eventRecords) {
                EventEntity event = eventRecordEventEntityMapper.recordToEntity(eventFrom);
                event.setStatus("STARTED");
                eventRepository.saveAndFlush(event);
            }
        }
    }

}
