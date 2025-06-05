package com.sg.reparos.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SG Pequenos Reparos - API")
                        .version("1.0")
                        .description("API para gestão de serviços de pequenos reparos.")
                        .contact(new Contact()
                                .name("Equipe SG Pequenos Reparos")
                                .email("contato@sgpequenosreparos.com")
                        )
                );
    }
}
