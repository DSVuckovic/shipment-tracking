package com.damjan_vuckovic.shipment_tracking.repository;

import com.damjan_vuckovic.shipment_tracking.model.Shipment;
import com.damjan_vuckovic.shipment_tracking.model.StatusChange.StatusChange;
import com.damjan_vuckovic.shipment_tracking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    Optional<Shipment> findByTrackingNumber(String trackingNumber);
    List<Shipment> findByCreatedById(Long userId);
}