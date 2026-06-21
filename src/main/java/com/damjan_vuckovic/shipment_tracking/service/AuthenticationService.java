package com.damjan_vuckovic.shipment_tracking.service;


import com.damjan_vuckovic.shipment_tracking.dto.authentication.AuthenticationRequestDto;
import com.damjan_vuckovic.shipment_tracking.dto.authentication.AuthenticationResponseDto;
import com.damjan_vuckovic.shipment_tracking.dto.user.UserRegistrationDto;
import com.damjan_vuckovic.shipment_tracking.mapper.UserMapper;
import com.damjan_vuckovic.shipment_tracking.model.User;
import com.damjan_vuckovic.shipment_tracking.repository.UserRepository;
import com.damjan_vuckovic.shipment_tracking.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    public AuthenticationResponseDto register(UserRegistrationDto request) {
        log.info("Registering user - Name: {} {}, Username: {}, Email: {}",
                request.getFirstname(),
                request.getLastname(),
                request.getUsername(),
                request.getEmail());
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            log.warn("Registration failed - username already exists: {}", request.getUsername());
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            log.warn("Registration failed - user with email {} already exists: {}",
                    request.getEmail(), request.getUsername());
            throw new IllegalArgumentException("Email already exists");
        }

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
        log.info("User registered succesfully: {}", request.getUsername());
        return new AuthenticationResponseDto(jwtUtil.generateToken(user.getUsername()));
    }

    public AuthenticationResponseDto login(AuthenticationRequestDto request) {
        log.info("Login attempt for user: {}", request.getUsername());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        log.info("User logged in successfully: {}", request.getUsername());
        return new AuthenticationResponseDto(jwtUtil.generateToken(user.getUsername()));
    }
}