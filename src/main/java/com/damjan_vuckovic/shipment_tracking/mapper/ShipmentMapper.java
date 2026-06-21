package com.damjan_vuckovic.shipment_tracking.mapper;

import com.damjan_vuckovic.shipment_tracking.dto.shipment.ShipmentReadDto;
import com.damjan_vuckovic.shipment_tracking.model.Shipment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ShipmentMapper {

    @Mapping(source = "createdBy.id", target = "createdBy")
    @Mapping(source = "status", target = "status")
    ShipmentReadDto toResponse(Shipment shipment);

    List<ShipmentReadDto> toResponseList(List<Shipment> shipments);
}