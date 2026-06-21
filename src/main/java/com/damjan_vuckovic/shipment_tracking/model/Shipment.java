package com.damjan_vuckovic.shipment_tracking.model;

import com.damjan_vuckovic.shipment_tracking.enums.EnumShipmentStatus;
import com.damjan_vuckovic.shipment_tracking.model.StatusChange;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "shipments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tracking_number", nullable = false, unique = true, length = 64)
    private String trackingNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="created_by", nullable = false)
    private User createdBy;

    @Column
    private String description;

    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private EnumShipmentStatus status;

    @OneToMany(mappedBy = "shipment", cascade = CascadeType.ALL)
    private List<StatusChange> statusChanges;


}
