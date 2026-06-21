package com.damjan_vuckovic.shipment_tracking.utils;

import com.damjan_vuckovic.shipment_tracking.model.User;
import com.damjan_vuckovic.shipment_tracking.security.CustomUserDetails;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class Utils {

    public static User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails customUserDetails)) {
            throw new AccessDeniedException("Not authenticated");
        }
        return customUserDetails.getDomainUser();
    }

    public static boolean isAdmin() {
        return SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}