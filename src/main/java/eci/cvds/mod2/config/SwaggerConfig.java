package eci.cvds.mod2.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Salas Crea API con Swagger",
        version = "1.0",
        description = "Documentación generada automáticamente con Swagger y OpenAPI"
    )
 )
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi apiDocs() {
        return GroupedOpenApi.builder()
             .group("v1") // Nombre de la versión o del grupo de documentación
             .pathsToMatch("/elements/**","/loans/**","/revs/**","/rooms/**")
             .addOpenApiCustomizer(removeSpringDataRestTags())
             .addOpenApiCustomizer(removeUnwantedSchemas())
             .build();
    }
    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT") // Opcional pero recomendado
                        )
                );
    }

    @Bean
    public OpenApiCustomizer removeSpringDataRestTags() {
        return openApi -> {
            // Remueve los paths asociados a los tags que quieres excluir
            if (openApi.getPaths() != null) {
                openApi.getPaths().entrySet().removeIf(pathEntry ->
                        pathEntry.getValue().readOperations().stream()
                                .anyMatch(operation -> {
                                    String tag = operation.getTags().stream().findFirst().orElse("");
                                    return tag.equalsIgnoreCase("loan-entity-controller")
                                            || tag.equalsIgnoreCase("loan-search-controller")
                                            || tag.equalsIgnoreCase("room-entity-controller")
                                            || tag.equalsIgnoreCase("room-search-controller");
                                })
                );
            }
        };
    }

    @Bean
    public OpenApiCustomizer removeUnwantedSchemas() {
        return openApi -> {
            if (openApi.getComponents() != null && openApi.getComponents().getSchemas() != null) {
                // Elimina los schemas innecesarios (según el nombre del schema)
                openApi.getComponents().getSchemas().entrySet().removeIf(entry ->
                                entry.getKey().equalsIgnoreCase("PageMetadata") ||
                                entry.getKey().equalsIgnoreCase("EntityModelRoom") ||
                                entry.getKey().equalsIgnoreCase("PagedModelEntityModelRoom") ||
                                entry.getKey().equalsIgnoreCase("CollectionModelEntityModelRoom") ||
                                entry.getKey().equalsIgnoreCase("EntityModelLoan") ||
                                entry.getKey().equalsIgnoreCase("PagedModelEntityModelLoan") ||
                                entry.getKey().equalsIgnoreCase("CollectionModelEntityModelLoan") ||
                                entry.getKey().equalsIgnoreCase("LoanRequestBody") ||
                                entry.getKey().equalsIgnoreCase("RoomRequestBody") ||
                                entry.getKey().equalsIgnoreCase("Link") ||
                                entry.getKey().equalsIgnoreCase("Links")
                );
            }
        };
    }




}