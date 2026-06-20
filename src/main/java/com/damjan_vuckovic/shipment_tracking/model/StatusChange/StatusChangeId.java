package com.damjan_vuckovic.shipment_tracking.model.StatusChange;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class StatusChangeId implements Serializable {

    @Column(name = "change_id")
    private Long changeId;

    @Column(name = "shipment_id")
    private Long shipmentId;
}
