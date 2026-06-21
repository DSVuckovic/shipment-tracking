package com.damjan_vuckovic.shipment_tracking.controller;

import com.damjan_vuckovic.shipment_tracking.dto.shipment.ShipmentChangeStatusDto;
import com.damjan_vuckovic.shipment_tracking.dto.shipment.ShipmentCreateDto;
import com.damjan_vuckovic.shipment_tracking.dto.shipment.ShipmentReadDto;
import com.damjan_vuckovic.shipment_tracking.dto.statuschange.StatusChangeReadDto;
import com.damjan_vuckovic.shipment_tracking.service.ShipmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
}
