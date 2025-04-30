package eci.cvds.mod2.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Mi API con Swagger",
        version = "1.0",
        description = "Documentación generada automáticamente con Swagger y OpenAPI"
    )
 )
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi apiDocs() {
        return GroupedOpenApi.builder()
             .group("v1") // Nombre de la versión o del grupo de documentación
             .pathsToMatch("/api/**") // Rutas que se incluirán en la documentación
             .build();
    }

}