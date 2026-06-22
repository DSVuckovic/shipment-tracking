package com.damjan_vuckovic.shipment_tracking.service.parser;

import com.damjan_vuckovic.shipment_tracking.dto.shipment.ShipmentCreateDto;
import com.damjan_vuckovic.shipment_tracking.dto.shipment.ShipmentImportDto;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ShipmentCSVParser implements ShipmentImportParser {

    @Override
    public Boolean isValidFileExtension(String filename) {
        return filename.endsWith(".csv");
    }

    @Override
    public List<ShipmentImportDto> parse(MultipartFile file) throws IOException {
        try (Reader reader = new InputStreamReader(file.getInputStream())) {
            return new CsvToBeanBuilder<ShipmentImportDto>(reader)
                    .withType(ShipmentImportDto.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build()
                    .parse();
        }
    }
}
