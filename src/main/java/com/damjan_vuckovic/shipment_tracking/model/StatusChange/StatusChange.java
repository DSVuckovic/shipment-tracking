package com.damjan_vuckovic.shipment_tracking.model.StatusChange;


import com.damjan_vuckovic.shipment_tracking.enums.EnumShipmentStatus;
import com.damjan_vuckovic.shipment_tracking.model.Shipment;
import com.damjan_vuckovic.shipment_tracking.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "status_change")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusChange {

    @EmbeddedId
    private StatusChangeId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("shipmentId")
    @JoinColumn(name = "shipment_id", nullable = false)
    private Shipment shipment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "changed_by", nullable = false)
    private User changedBy;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "old_status", nullable = false)
    private EnumShipmentStatus oldStatus;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "new_status", nullable = false)
    private EnumShipmentStatus newStatus;

    @Column(name = "changed_at", nullable = false)
    private LocalDateTime changedAt;

    @Column
    private String description;
}
