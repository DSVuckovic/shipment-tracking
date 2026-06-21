package com.damjan_vuckovic.shipment_tracking.service;


import com.damjan_vuckovic.shipment_tracking.dto.statuschange.StatusChangeReadDto;
import com.damjan_vuckovic.shipment_tracking.mapper.StatusChangeMapper;
import com.damjan_vuckovic.shipment_tracking.model.StatusChange;
import com.damjan_vuckovic.shipment_tracking.model.User;
import com.damjan_vuckovic.shipment_tracking.repository.StatusChangeRepository;
import com.damjan_vuckovic.shipment_tracking.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatusChangeService {

    private final StatusChangeRepository statusChangeRepository;
    private final StatusChangeMapper statusChangeMapper;

    public List<StatusChangeReadDto> getAll() {
        User currentUser = Utils.getCurrentUser();
        boolean isAdmin = Utils.isAdmin();

        List<StatusChange> shipments = statusChangeRepository.findAll();

        return statusChangeMapper.toResponseList(shipments);
    }
}
