package com.damjan_vuckovic.shipment_tracking.model;


import com.damjan_vuckovic.shipment_tracking.enums.EnumShipmentStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Table(name = "status_changes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusChange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "change_id")
    private Long changeId;

    @ManyToOne(fetch = FetchType.LAZY)
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
