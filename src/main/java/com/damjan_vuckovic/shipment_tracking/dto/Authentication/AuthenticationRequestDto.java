package com.damjan_vuckovic.shipment_tracking.dto.Authentication;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequestDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}
