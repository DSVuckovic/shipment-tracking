package com.damjan_vuckovic.shipment_tracking.controller;

import com.damjan_vuckovic.shipment_tracking.dto.shipment.ShipmentBulkImportResultsDto;
import com.damjan_vuckovic.shipment_tracking.dto.shipment.ShipmentChangeStatusDto;
import com.damjan_vuckovic.shipment_tracking.dto.shipment.ShipmentCreateDto;
import com.damjan_vuckovic.shipment_tracking.dto.shipment.ShipmentReadDto;
import com.damjan_vuckovic.shipment_tracking.dto.statuschange.StatusChangeReadDto;
import com.damjan_vuckovic.shipment_tracking.enums.EnumShipmentStatus;
import com.damjan_vuckovic.shipment_tracking.service.ShipmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Shipment API", description = "Endpoints for shipment data manipulation")
public class ShipmentController {

    private final ShipmentService shipmentService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Create a new shipment", description = "Creates a new shipment with new tracking number and description")
    public @ResponseBody ShipmentReadDto create(@Valid @RequestBody ShipmentCreateDto request) {
        return shipmentService.create(request);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Fetches all shipments (Testing, use search instead)", description = "Fetches all shipments for current user (or all shipments if the user has admin privileges")
    public @ResponseBody List<ShipmentReadDto> getAll() {
        return shipmentService.getAll();
    }

    @GetMapping("/search")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Search for shipments", description = "Returns all shipments based on given criteria belonging to current user (or all if user has admin privileges")
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
    @Operation(summary = "Fetch a specific shipment", description = "Fetches a specific shipment through its id")
    public @ResponseBody ShipmentReadDto getById(@PathVariable Long id) {
        return shipmentService.getById(id);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Change shipment status", description = "Changes the status of a shipment and logs it into history")
    public @ResponseBody ShipmentReadDto updateStatus(@PathVariable Long id,
                                         @Valid @RequestBody ShipmentChangeStatusDto request) {
        return shipmentService.updateStatus(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a shipment", description = "Completely deletes a shipment and its status history")
    public @ResponseBody ShipmentReadDto delete(@PathVariable Long id) {
        return shipmentService.delete(id);
    }

    @GetMapping("/{id}/history")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Fetch shipment history", description = "Returns shipment history")
    public List<StatusChangeReadDto> getHistory(@PathVariable Long id) {
        return shipmentService.getHistory(id);
    }

    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Import shipments from CSV or Excel file", description = "Adds shipments from file")
    public ShipmentBulkImportResultsDto importShipments(@RequestPart("file") MultipartFile file)
            throws IOException {
        return shipmentService.importShipments(file);
    }
}
