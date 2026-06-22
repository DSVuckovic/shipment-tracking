package com.damjan_vuckovic.shipment_tracking.service;

import com.damjan_vuckovic.shipment_tracking.dto.shipment.ShipmentChangeStatusDto;
import com.damjan_vuckovic.shipment_tracking.dto.shipment.ShipmentCreateDto;
import com.damjan_vuckovic.shipment_tracking.dto.shipment.ShipmentReadDto;
import com.damjan_vuckovic.shipment_tracking.dto.statuschange.StatusChangeReadDto;
import com.damjan_vuckovic.shipment_tracking.enums.EnumShipmentStatus;
import com.damjan_vuckovic.shipment_tracking.exception.ResourceNotFoundException;
import com.damjan_vuckovic.shipment_tracking.mapper.ShipmentMapper;
import com.damjan_vuckovic.shipment_tracking.mapper.StatusChangeMapper;
import com.damjan_vuckovic.shipment_tracking.model.Shipment;
import com.damjan_vuckovic.shipment_tracking.model.StatusChange;
import com.damjan_vuckovic.shipment_tracking.model.User;
import com.damjan_vuckovic.shipment_tracking.repository.ShipmentRepository;
import com.damjan_vuckovic.shipment_tracking.repository.StatusChangeRepository;
import com.damjan_vuckovic.shipment_tracking.specification.ShipmentSpecifications;
import com.damjan_vuckovic.shipment_tracking.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final StatusChangeRepository statusChangeRepository;
    private final ShipmentMapper shipmentMapper;
    private final StatusChangeMapper statusChangeMapper;

    public ShipmentReadDto create(ShipmentCreateDto request) {
        log.info("Creating shipment with tracking number: {}", request.getTrackingNumber());

        if (shipmentRepository.findByTrackingNumber(request.getTrackingNumber()).isPresent()) {
            log.warn("Tracking number already exists: {}", request.getTrackingNumber());
            throw new IllegalArgumentException("Tracking number already exists");
        }

        User currentUser = Utils.getCurrentUser();
        Shipment shipment = Shipment.builder()
                .trackingNumber(request.getTrackingNumber())
                .description(request.getDescription())
                .createdBy(currentUser)
                .createdAt(LocalDateTime.now())
                .status(EnumShipmentStatus.CREATED)
                .build();

        Shipment newShipment = shipmentRepository.save(shipment);
        log.info("Shipment created successfully: {}", shipment.getTrackingNumber());
        return shipmentMapper.toResponse(newShipment);
    }

    public List<ShipmentReadDto> getAll() {
        User currentUser = Utils.getCurrentUser();
        boolean isAdmin = Utils.isAdmin();

        List<Shipment> shipments = isAdmin
                ? shipmentRepository.findAll()
                : shipmentRepository.findByCreatedById(currentUser.getId());

        return shipmentMapper.toResponseList(shipments);
    }

    public Page<ShipmentReadDto> search(String trackingNumber, Long userId, String description,
                                        EnumShipmentStatus status, LocalDateTime createdFrom, LocalDateTime createdTo, int page, int pageSize) {
        User currentUser = Utils.getCurrentUser();
        boolean isAdmin = Utils.isAdmin();
        return shipmentRepository.findAll(
                ShipmentSpecifications.searchFilters(trackingNumber,
                        isAdmin ? userId : currentUser.getId(), description, status, createdFrom, createdTo), PageRequest.of(page, pageSize))
                .map(shipmentMapper::toResponse);


    }

    public ShipmentReadDto getById(Long id) {
        User currentUser = Utils.getCurrentUser();
        boolean isAdmin = Utils.isAdmin();

        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment not found"));

        if (!isAdmin && !shipment.getCreatedBy().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("Access denied");
        }

        return shipmentMapper.toResponse(shipment);
    }


    public ShipmentReadDto updateStatus(Long id, ShipmentChangeStatusDto request) {
        log.info("Updating status for shipment id: {}", id);

        User currentUser = Utils.getCurrentUser();
        boolean isAdmin = Utils.isAdmin();

        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment not found"));

        if (!isAdmin && !shipment.getCreatedBy().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("Access denied");
        }

        EnumShipmentStatus oldStatus = shipment.getStatus();
        shipment.setStatus(request.getNewStatus());
        shipmentRepository.save(shipment);

        StatusChange statusChange = StatusChange.builder()
                .shipment(shipment)
                .changedBy(currentUser)
                .oldStatus(oldStatus)
                .newStatus(request.getNewStatus())
                .changedAt(LocalDateTime.now())
                .description(request.getDescription())
                .build();

        statusChangeRepository.save(statusChange);
        log.info("Status updated from {} to {}", oldStatus, request.getNewStatus());
        return shipmentMapper.toResponse(shipment);
    }

    public ShipmentReadDto delete(Long id) {
        log.info("Deleting shipment id: {}", id);
        boolean isAdmin = Utils.isAdmin();

        if (!isAdmin) {
            throw new AccessDeniedException("Access denied");
        }

        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment not found"));

        shipmentRepository.delete(shipment);
        log.info("Shipment deleted successfully: {}", id);
        return shipmentMapper.toResponse(shipment);
    }

    public List<StatusChangeReadDto> getHistory(Long shipmentId) {
        User currentUser = Utils.getCurrentUser();
        boolean isAdmin = Utils.isAdmin();

        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment not found"));

        if (!isAdmin && !shipment.getCreatedBy().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("Access denied");
        }

        return statusChangeMapper.toResponseList(
                statusChangeRepository.findByShipmentId(shipmentId));
    }

}
