package com.damjan_vuckovic.shipment_tracking.repository;

import com.damjan_vuckovic.shipment_tracking.model.StatusChange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusChangeRepository extends JpaRepository<StatusChange, Long> {
    List<StatusChange> findByShipmentId(Long shipmentId);
}
