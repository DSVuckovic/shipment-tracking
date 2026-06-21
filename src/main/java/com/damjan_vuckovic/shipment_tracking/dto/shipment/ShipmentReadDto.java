package com.damjan_vuckovic.shipment_tracking.dto.shipment;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShipmentReadDto {
    private Long id;
    private String trackingNumber;
    private String description;
    private String status;
    private String createdBy;
    private LocalDateTime createdAt;
}
