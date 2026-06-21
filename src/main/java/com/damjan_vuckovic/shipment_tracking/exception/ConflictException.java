package com.damjan_vuckovic.shipment_tracking.exception;


public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}