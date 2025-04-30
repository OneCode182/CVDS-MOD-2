package eci.cvds.mod2.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@Tag(name = "API de Salud", description = "Operaciones para verificar el estado de la API")
public class ApiController {

    // Endpoint GET para verificar si la API funciona
    @GetMapping("/health")
    @Operation(summary = "Verifica el estado de la API", description = "Este endpoint devuelve un mensaje indicando que la API está funcionando correctamente.")
    public String checkHealth() {
        return "API está funcionando correctamente";
    }
}
