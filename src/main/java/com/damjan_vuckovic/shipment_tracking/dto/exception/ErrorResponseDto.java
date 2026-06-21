package com.damjan_vuckovic.shipment_tracking.dto.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponseDto {
    int status;
    String message;
    LocalDateTime timestamp;
}
