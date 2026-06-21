package com.damjan_vuckovic.shipment_tracking.controller;

import com.damjan_vuckovic.shipment_tracking.dto.statuschange.StatusChangeReadDto;
import com.damjan_vuckovic.shipment_tracking.service.StatusChangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/status-changes")
@RequiredArgsConstructor
public class StatusChangeController {

    private final StatusChangeService statusChangeService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody List<StatusChangeReadDto> getAll() {
        return statusChangeService.getAll();
    }
}
