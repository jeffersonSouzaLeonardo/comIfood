package br.com.lett.comifood.apicom;

import br.com.lett.comifood.enuns.EnterpriseStatus;
import br.com.lett.comifood.model.EnterpriseEntity;
import br.com.lett.comifood.model.EventEntity;
import br.com.lett.comifood.record.EventRecord;
import br.com.lett.comifood.rest.EventRest;
import br.com.lett.comifood.service.EnterpriseService;
import br.com.lett.comifood.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class EventApiCom {

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private EventRest eventRest;

    @Autowired
    private EventService eventService;

    public void polling(){

        for (EnterpriseEntity enterprise : enterpriseService.findByStatus(EnterpriseStatus.ACTIVE)) {

            List<EventRecord> eventRecords = eventRest.polling(enterprise);

            List<EventEntity> eventEntities = new ArrayList<>();
            for (EventRecord eventRecord : eventRecords) {
                if ( eventService.findByIdIfood(eventRecord.id()).size() == 0 ) {
                    EventEntity event = eventService.eventRecordEToventEntityMapper(eventRecord);
                    event.setStatus("STARTED");
                    eventService.save(event);
                }
            }
        }
    }

}
