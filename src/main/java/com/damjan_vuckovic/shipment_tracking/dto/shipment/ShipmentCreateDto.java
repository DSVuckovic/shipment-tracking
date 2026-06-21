package com.damjan_vuckovic.shipment_tracking.dto.shipment;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentCreateDto {

    @NotBlank
    private String trackingNumber;

    @NotBlank
    private String description;
}
