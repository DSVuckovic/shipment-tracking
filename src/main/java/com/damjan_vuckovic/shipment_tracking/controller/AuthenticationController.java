package com.damjan_vuckovic.shipment_tracking.controller;

import com.damjan_vuckovic.shipment_tracking.dto.authentication.AuthenticationRequestDto;
import com.damjan_vuckovic.shipment_tracking.dto.authentication.AuthenticationResponseDto;
import com.damjan_vuckovic.shipment_tracking.dto.user.UserRegistrationDto;
import com.damjan_vuckovic.shipment_tracking.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/register")
    public AuthenticationResponseDto register(@Valid @RequestBody UserRegistrationDto request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthenticationResponseDto login(@Valid @RequestBody AuthenticationRequestDto request) {
        return authService.login(request);
    }
}