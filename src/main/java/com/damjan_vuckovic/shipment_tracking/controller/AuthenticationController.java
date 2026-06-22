package com.damjan_vuckovic.shipment_tracking.controller;

import com.damjan_vuckovic.shipment_tracking.dto.authentication.AuthenticationRequestDto;
import com.damjan_vuckovic.shipment_tracking.dto.authentication.AuthenticationResponseDto;
import com.damjan_vuckovic.shipment_tracking.dto.user.UserRegistrationDto;
import com.damjan_vuckovic.shipment_tracking.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Tag(name = "User API", description = "Endpoints for authentication (login/registration)")
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Register a new user with non-admin privileges")
    public AuthenticationResponseDto register(@Valid @RequestBody UserRegistrationDto request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    @Operation(summary = "Login as a pre-existing user", description = "Returns a Bearer token for authentication")
    public AuthenticationResponseDto login(@Valid @RequestBody AuthenticationRequestDto request) {
        return authService.login(request);
    }
}