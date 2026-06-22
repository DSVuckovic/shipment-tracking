package com.damjan_vuckovic.shipment_tracking.service.parser;

import com.damjan_vuckovic.shipment_tracking.dto.shipment.ShipmentCreateDto;
import com.damjan_vuckovic.shipment_tracking.dto.shipment.ShipmentImportDto;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ShipmentExcelParser implements ShipmentImportParser {

    @Override
    public Boolean isValidFileExtension(String filename) {
        return filename.endsWith(".xlsx") || filename.endsWith(".xls");
    }

    @Override
    public List<ShipmentImportDto> parse(MultipartFile file) throws IOException {
        List<ShipmentImportDto> requests = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            for(Sheet sheet: workbook){
                boolean firstRow = true;
                for (Row row : sheet) {
                    if (firstRow) {
                        firstRow = false;
                        continue;
                    }
                    Cell trackingCell = row.getCell(0);
                    Cell descCell = row.getCell(1);
                    Cell statusCell = row.getCell(2);
                    Cell dateCell = row.getCell(3);

                    if (trackingCell != null && descCell != null  && statusCell != null && dateCell != null) {
                        requests.add(new ShipmentImportDto(
                                trackingCell.getStringCellValue().trim(),
                                descCell.getStringCellValue().trim(),
                                statusCell.getStringCellValue().trim(),
                                dateCell.getStringCellValue().trim()
                        ));
                    }
                }
            }
        }
        return requests;
    }
}
