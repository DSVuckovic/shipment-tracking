package com.damjan_vuckovic.shipment_tracking.service.parser;

import com.damjan_vuckovic.shipment_tracking.dto.shipment.ShipmentCreateDto;
import com.damjan_vuckovic.shipment_tracking.dto.shipment.ShipmentImportDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ShipmentImportParser {

    Boolean isValidFileExtension(String filename);

    List<ShipmentImportDto> parse(MultipartFile file) throws IOException;

}
