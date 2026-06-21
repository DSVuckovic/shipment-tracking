package com.damjan_vuckovic.shipment_tracking.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        final String schemeName = "Authorization";

        return new OpenAPI()
                .info(new Info()
                        .title("Shipment Tracking API")
                        .description("OpenAPI documentation")
                        .version("v1"))
                .components(new Components()
                        .addSecuritySchemes(schemeName,
                                new SecurityScheme()
                                        .name(schemeName)
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER)
                                        .description("Send JWT as: Bearer <token> in Authorization header")))
                .addSecurityItem(new SecurityRequirement().addList(schemeName));
    }
}
