package com.damjan_vuckovic.shipment_tracking.specification;

import com.damjan_vuckovic.shipment_tracking.enums.EnumShipmentStatus;
import com.damjan_vuckovic.shipment_tracking.model.Shipment;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;


public class ShipmentSpecifications {

    public static Specification<Shipment> searchFilters(String trackingNumber, Long userId, String description,
                                                 EnumShipmentStatus status, LocalDateTime createdFrom, LocalDateTime createdTo) {
        return Specification
                .where(likeTrackingNumber(trackingNumber))
                .and(byUserId(userId))
                .and(likeDescription(description))
                .and(byStatus(status))
                .and(fromDate(createdFrom))
                .and(toDate(createdTo));
    }

    private static Specification<Shipment> likeTrackingNumber(String value) {
        return (root, query, criteriaBuilder) -> value == null || value.isBlank() ? null :
                criteriaBuilder.like(criteriaBuilder.lower(root.get("trackingNumber")), "%" + value.toLowerCase() + "%");
    }

    private static Specification<Shipment> likeDescription(String value) {
        return (root, query, criteriaBuilder) -> value == null || value.isBlank() ? null :
                criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + value.toLowerCase() + "%");
    }

    private static Specification<Shipment> byUserId(Long userId) {
        return (root, query, criteriaBuilder) -> userId == null ? null :
                criteriaBuilder.equal(root.get("createdBy").get("id"), userId);
    }

    private static Specification<Shipment> byStatus(EnumShipmentStatus status) {
        return (root, query, criteriaBuilder) -> status == null ? null :
                criteriaBuilder.equal(root.get("status"), status);
    }

    private static Specification<Shipment> fromDate(LocalDateTime from) {
        return (root, query, criteriaBuilder) -> from == null ? null :
                criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), from);
    }

    private static Specification<Shipment> toDate(LocalDateTime to) {
        return (root, query, criteriaBuilder) -> to == null ? null :
                criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), to);
    }
}
