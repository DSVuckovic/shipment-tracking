package com.damjan_vuckovic.shipment_tracking.mapper;

import com.damjan_vuckovic.shipment_tracking.dto.User.UserRegistrationDto;
import com.damjan_vuckovic.shipment_tracking.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", constant = "USER")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "shipments", ignore = true)
    User toEntity(UserRegistrationDto request);
}
