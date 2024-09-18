package br.com.lett.comifood.service;

import br.com.lett.comifood.mapper.EventRecordEventEntityMapper;
import br.com.lett.comifood.model.EventEntity;
import br.com.lett.comifood.record.EventRecord;
import br.com.lett.comifood.repository.EnterpriseRepository;
import br.com.lett.comifood.repository.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class EventService {

    @Autowired
    private EnterpriseRepository enterpriseRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    EventRecordEventEntityMapper eventRecordEventEntityMapper;

    public EventEntity eventRecordEToventEntityMapper(EventRecord eventRecord){
        return eventRecordEventEntityMapper.recordToEntity(eventRecord);
    }

    public EventEntity save(EventEntity eventEntity){
        try{

            eventEntity = eventRepository.saveAndFlush(eventEntity);

        } catch (Exception e){
            log.error("Erro ao salvar Evento! Evento MerchantId: " +
                       eventEntity.getMerchantId() +
                       " - id Ifood: " +
                       eventEntity.getIdIfood() +
                       " - " + e.getMessage()
            );
        }

        return eventEntity;
    }

    public List<EventEntity> findByIdIfood(String idIfood){
        return eventRepository.findByIdIfood(idIfood);
    }


}
