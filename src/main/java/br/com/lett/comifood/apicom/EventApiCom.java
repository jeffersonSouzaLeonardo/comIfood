package br.com.lett.comifood.apicom;

import br.com.lett.comifood.apicom.observerEvent.CustomEvent;
import br.com.lett.comifood.enuns.EnterpriseStatus;
import br.com.lett.comifood.model.EnterpriseEntity;
import br.com.lett.comifood.model.EventEntity;
import br.com.lett.comifood.record.AknowledgmentRecord;
import br.com.lett.comifood.record.EventRecord;
import br.com.lett.comifood.rest.AknowledgmentRest;
import br.com.lett.comifood.rest.EventRest;
import br.com.lett.comifood.service.EnterpriseService;
import br.com.lett.comifood.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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

    @Autowired
    private AknowledgmentRest aknowledgmentRest;

    @Autowired
    private ApplicationEventPublisher publisher;

    public void polling(){

        List<AknowledgmentRecord> aknowledgmentRecordsList = new ArrayList<>();

        for (EnterpriseEntity enterprise : enterpriseService.findByStatus(EnterpriseStatus.ACTIVE)) {

            List<EventRecord> eventRecords = eventRest.polling(enterprise);

            List<EventEntity> eventEntities = new ArrayList<>();
            for (EventRecord eventRecord : eventRecords) {
                if ( eventService.findByIdIfood(eventRecord.id()).size() == 0 ) {
                    EventEntity event = eventService.eventRecordEToventEntityMapper(eventRecord);
                    event.setStatus("RECEIVED");
                    eventService.save(event);
                    AknowledgmentRecord aknowledgmentRecord = new AknowledgmentRecord(event.getIdIfood(), event.getOrderId(), enterprise.getToken());
                    aknowledgmentRecordsList.add(aknowledgmentRecord);
                }
            }
            if (aknowledgmentRecordsList.size() > 0) {
                log.info("Evio de Aknowledgemnt, empresa: " + enterprise.getId() + " - " + enterprise.getEnterprise());
                publisher.publishEvent(new CustomEvent(aknowledgmentRecordsList));
                aknowledgmentRecordsList.clear();
            }
        }
    }

}
