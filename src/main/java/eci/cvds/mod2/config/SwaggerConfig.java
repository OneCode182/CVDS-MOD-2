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
             .group("v1")
             .pathsToMatch("/api/**")
             .build();
    }

}