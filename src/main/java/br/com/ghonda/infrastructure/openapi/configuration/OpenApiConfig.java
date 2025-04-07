package br.com.ghonda.infrastructure.openapi.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Comparator;
import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                      .title("Sistema de Gestão Institucional")
                      .version("1.0.0")
                      .description("API para gestão de servidores efetivos, temporários, unidades e lotações")
                      .contact(new Contact()
                                   .name("Gabriel Honda")
                                   .email("gabriel.honda@example.com"))
            )
            .servers(List.of(
                new Server()
                    .url("http://localhost:8080")
                    .description("Local ENV"))
            )
            .schemaRequirement(
                "bearerAuth",
                new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
                    .description("JWT auth description")
                    .in(SecurityScheme.In.HEADER)
            );
    }

    @Bean
    public OpenApiCustomizer sortTagsAlphabetically() {
        return openApi -> {
            final var tags = openApi.getTags();
            if (tags != null) {
                tags.sort(Comparator.comparing(Tag::getName));
                openApi.setTags(tags);
            }
        };
    }

}
