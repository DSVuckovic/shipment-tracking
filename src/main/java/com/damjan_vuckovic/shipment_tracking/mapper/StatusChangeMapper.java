package com.damjan_vuckovic.shipment_tracking.mapper;

import com.damjan_vuckovic.shipment_tracking.dto.statuschange.StatusChangeReadDto;
import com.damjan_vuckovic.shipment_tracking.model.StatusChange;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StatusChangeMapper {

    @Mapping(source = "changedBy.id", target = "changedBy")
    StatusChangeReadDto toResponse(StatusChange statusChange);

    List<StatusChangeReadDto> toResponseList(List<StatusChange> statusChanges);
}
