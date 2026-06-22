package com.damjan_vuckovic.shipment_tracking.dto.shipment;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ShipmentBulkImportResultsDto {

    private int successful;
    private int failed;
    private List<String> errors;
}
