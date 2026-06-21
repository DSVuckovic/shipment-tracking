package com.damjan_vuckovic.shipment_tracking.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;

@Component
@Slf4j
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, @NonNull AuthenticationException ex)
            throws IOException {

        log.error("=> Access denied for method > {}  on path > {} from host > {} with ip > {}", req.getMethod(),
                req.getRequestURI(), InetAddress.getLocalHost().getHostName(),
                InetAddress.getLocalHost().getHostAddress());

        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        res.setStatus(HttpStatus.UNAUTHORIZED.value());
        res.getWriter().write("{\"message\": \"Cannot access this resource without an account. Please login\"}");
    }

}
