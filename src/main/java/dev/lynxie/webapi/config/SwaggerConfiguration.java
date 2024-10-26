package dev.lynxie.webapi.config;

import dev.lynxie.webapi.master.exception.response.ApiError;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;

@OpenAPIDefinition(
        info = @Info(
                title = "The Lynxie Core Service",
                version = "1.0",
                contact = @Contact(
                        name = "Support",
                        url = "https://t.me/exc3pt1ontg",
                        email = "shldimka@gmail.com"
                )
        ),
        servers = {
                @Server(url = "/", description = "Default URL")
        }
)
@Configuration
public class SwaggerConfiguration {
    private static final List<String> NOT_AUTHENTICATED_ENDPOINTS = List.of("login", "register", "docs", "health");

    @Bean
    public GlobalOpenApiCustomizer customOpenApiCustomizer() {
        return openApi -> {
            Components components = openApi.getComponents();

            if (components.getSchemas() == null) {
                components.setSchemas(new HashMap<>());
            }

            openApi.getComponents().getSchemas().putAll(
                    ModelConverters.getInstance().read(ApiError.class)
            );

            openApi.addSecurityItem(
                    new SecurityRequirement().addList("Bearer Authentication")
            );
            openApi.getComponents().addSecuritySchemes("Bearer Authentication", createAPIKeySchema());

            openApi.getPaths().values().forEach(pathItem -> {
                pathItem.readOperations().forEach(operation -> {
                    if (!NOT_AUTHENTICATED_ENDPOINTS.contains(operation.getOperationId())) {
                        operation.addParametersItem(
                                new Parameter().in("header").name("X-Technical").description("Choose technical")
                        );
                    }

                    ApiResponses apiResponses = operation.getResponses();
                    apiResponses.addApiResponse("400", createApiResponse("Bad user data request"));
                    apiResponses.addApiResponse("401", createApiResponse("Unauthorized"));
                });
            });
        };
    }

    private SecurityScheme createAPIKeySchema() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

    private ApiResponse createApiResponse(String message) {
        MediaType mediaType = new MediaType();
        return new ApiResponse()
                .description(message)
                .content(
                        new Content().addMediaType("application/json", mediaType)
                );
    }
}
