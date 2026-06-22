package com.damjan_vuckovic.shipment_tracking.controller;

import com.damjan_vuckovic.shipment_tracking.dto.shipment.ShipmentBulkImportResultsDto;
import com.damjan_vuckovic.shipment_tracking.dto.shipment.ShipmentChangeStatusDto;
import com.damjan_vuckovic.shipment_tracking.dto.shipment.ShipmentCreateDto;
import com.damjan_vuckovic.shipment_tracking.dto.shipment.ShipmentReadDto;
import com.damjan_vuckovic.shipment_tracking.dto.statuschange.StatusChangeReadDto;
import com.damjan_vuckovic.shipment_tracking.enums.EnumShipmentStatus;
import com.damjan_vuckovic.shipment_tracking.service.ShipmentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/shipments")
@RequiredArgsConstructor
public class ShipmentController {

    private final ShipmentService shipmentService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody ShipmentReadDto create(@Valid @RequestBody ShipmentCreateDto request) {
        return shipmentService.create(request);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody List<ShipmentReadDto> getAll() {
        return shipmentService.getAll();
    }

    @GetMapping("/search")
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody Page<ShipmentReadDto> search(
            @RequestParam(required = false) String trackingNumber,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) EnumShipmentStatus shipmentStatus,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime createdFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime createdTo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int pageSize
            ) {
        return shipmentService.search(trackingNumber, userId, description, shipmentStatus, createdFrom, createdTo, page, pageSize);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody ShipmentReadDto getById(@PathVariable Long id) {
        return shipmentService.getById(id);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody ShipmentReadDto updateStatus(@PathVariable Long id,
                                         @Valid @RequestBody ShipmentChangeStatusDto request) {
        return shipmentService.updateStatus(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody ShipmentReadDto delete(@PathVariable Long id) {
        return shipmentService.delete(id);
    }

    @GetMapping("/{id}/history")
    @PreAuthorize("isAuthenticated()")
    public List<StatusChangeReadDto> getHistory(@PathVariable Long id) {
        return shipmentService.getHistory(id);
    }

    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Import shipments from CSV or Excel file")
    public ShipmentBulkImportResultsDto importShipments(@RequestPart("file") MultipartFile file)
            throws IOException {
        return shipmentService.importShipments(file);
    }
}
