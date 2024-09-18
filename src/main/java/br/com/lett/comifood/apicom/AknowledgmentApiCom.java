package br.com.lett.comifood.apicom;

import br.com.lett.comifood.apicom.observerEvent.CustomEvent;
import br.com.lett.comifood.record.AknowledgmentRecord;
import br.com.lett.comifood.repository.EventRepository;
import br.com.lett.comifood.rest.AknowledgmentRest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AknowledgmentApiCom {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private AknowledgmentRest aknowledgmentRest;

    @EventListener
    public void handleCustomEvent(CustomEvent event) {
        aknowledgmentRest.postAknowledgment((List<AknowledgmentRecord>) event.getSource());

    }


}
