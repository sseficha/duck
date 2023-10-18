package com.solonsef.duck;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Duck API",
                description = "A simple api for anyone who loves ducks",
                contact = @Contact(
                        name = "Solon Seficha",
                        email = "solonsef@gmail.com"
                )))
@SecurityScheme(
        name = "basic",
        scheme = "basic",
        type = SecuritySchemeType.HTTP)
public class OpenAPIConfiguration {
}
