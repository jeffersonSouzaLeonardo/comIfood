package br.com.lett.comifood.mapper;

import br.com.lett.comifood.model.EventEntity;
import br.com.lett.comifood.record.EventRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventRecordEventEntityMapper{
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "idIfood", source = "id")
    EventEntity recordToEntity(EventRecord eventRecord);
}


