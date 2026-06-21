package com.damjan_vuckovic.shipment_tracking.dto.shipment;


import com.damjan_vuckovic.shipment_tracking.enums.EnumShipmentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentChangeStatusDto {

    @NotNull
    private EnumShipmentStatus newStatus;

    private String description;
}
