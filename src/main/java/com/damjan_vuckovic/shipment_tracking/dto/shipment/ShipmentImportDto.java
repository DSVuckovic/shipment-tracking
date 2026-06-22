package com.damjan_vuckovic.shipment_tracking.dto.shipment;


import com.opencsv.bean.CsvBindByName;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentImportDto {

    @NotBlank
    @CsvBindByName(column = "Tracking Number")
    private String trackingNumber;

    @NotBlank
    @CsvBindByName(column = "Description")
    private String description;

    @CsvBindByName(column = "Status")
    private String status;

    @CsvBindByName(column = "Created At")
    private String createdAt;

}
