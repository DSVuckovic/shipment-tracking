package com.damjan_vuckovic.shipment_tracking.dto.statuschange;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusChangeReadDto {
    private Long changeId;
    private String oldStatus;
    private String newStatus;
    private String changedBy;
    private LocalDateTime changedAt;
    private String description;
}
