package eci.cvds.mod2.controllers;

import eci.cvds.mod2.modules.RecreationalElement;
import eci.cvds.mod2.modules.Room;
import eci.cvds.mod2.services.ElementsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/elements")
@CrossOrigin(origins = "*")
@Tag(name = "Elementos Recreativos", description = "Operaciones relacionadas con elementos recreativos")
public class ElementsController {
    ElementsService elementsService;
    @Autowired
    public ElementsController(ElementsService elementsService){
        this.elementsService=elementsService;
    }

    @Operation(
            summary = "Obtener un elemento por ID",
            description = "Retorna un elemento recreativo dado su identificador.",
            parameters = {
                    @Parameter(name = "elementId", description = "ID del elemento", required = true, example = "642b7f1acb0a1d0001fae3d4")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Elemento encontrado",
                            content = @Content(schema = @Schema(implementation = RecreationalElement.class))),
                    @ApiResponse(responseCode = "404", description = "The Element searched was not found")
            }
    )
    @GetMapping("/id/{elementId}")
    public ResponseEntity<RecreationalElement> getElementById(@PathVariable  String elementId) {
        RecreationalElement element = elementsService.getElementById(elementId);
        return ResponseEntity.ok(element);
    }

    @Operation(
            summary = "Obtener un elemento por nombre",
            description = "Retorna un elemento recreativo dado su nombre.",
            parameters = {
                    @Parameter(name = "elementName", description = "Nombre del elemento", required = true, example = "Uno")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Elemento encontrado",
                            content = @Content(schema = @Schema(implementation = RecreationalElement.class))),
                    @ApiResponse(responseCode = "404", description = "The Element searched was not found")
            }
    )
    @GetMapping("/name/{elementName}")
    public ResponseEntity<RecreationalElement> getElementByName(@PathVariable String elementName) {
        RecreationalElement element = elementsService.getElementByName(elementName);
        return ResponseEntity.ok(element);
    }


    @Operation(
            summary = "Crear un nuevo elemento",
            description = "Crea un nuevo elemento recreativo con la información proporcionada.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Objeto del nuevo elemento a crear",
                    required = true,
                    content = @Content(schema = @Schema(implementation = RecreationalElement.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Element successfully created")
            }
    )
    @PostMapping
    public ResponseEntity<String> createElement(@Valid @RequestBody RecreationalElement element) {
        elementsService.createElement(element);
        return ResponseEntity.status(HttpStatus.CREATED).body("Element successfully created");
    }


    @Operation(
            summary = "Eliminar un elemento",
            description = "Elimina un elemento recreativo según su ID.",
            parameters = {
                    @Parameter(name = "elementId", description = "ID del elemento a eliminar", required = true, example = "642b7f1acb0a1d0001fae3d4")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Element successfully deleted"),
                    @ApiResponse(responseCode = "404", description = "The Element searched was not found")
            }
    )
    @DeleteMapping("/{elementId}")
    public ResponseEntity<String> deleteElement(@PathVariable String elementId) {
        // Verifica si el elemento existe llamando a getElementById
        elementsService.getElementById(elementId);  // Esto asegura que el elemento se obtenga antes de intentar eliminarlo.

        elementsService.deleteElement(elementId);
        return ResponseEntity.ok("Element successfully deleted");
    }

    @Operation(
            summary = "Actualizar un elemento existente",
            description = "Actualiza un elemento recreativo existente con la información proporcionada.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Nuevo objeto con los datos del elemento a actualizar",
                    required = true,
                    content = @Content(schema = @Schema(implementation = RecreationalElement.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Element successfully updated"),
                    @ApiResponse(responseCode = "404", description = "The Element searched was not found")
            }
    )
    @PutMapping("/{elementId}")
    public ResponseEntity<String> updateElement(@PathVariable String elementId,@Valid @RequestBody RecreationalElement newElement){
        elementsService.updateElement(elementId, newElement);
        return ResponseEntity.ok("Element successfully updated");
    }


    @Operation(
            summary = "Obtener todos los elementos",
            description = "Retorna un listado de todos los elementos recreativos.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Elementos encontrados",
                            content = @Content(schema = @Schema(implementation = RecreationalElement.class)))
            }
    )
    @GetMapping
    public List<RecreationalElement> getAll(){
        return elementsService.getAll();
    }
    @PutMapping("/reduce/{elementId}")
    public ResponseEntity<String> reduceElementQuantity(@PathVariable String elementId, int loanNumber){
        elementsService.reduceElementQuantity(elementId, loanNumber);
        return ResponseEntity.ok("The capacity was successfully reduced");
    }
    @Operation(summary = "Aumentar la capacidad de una sala")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The capacity was successfully increased",
                    content = @Content(schema = @Schema(implementation = Room.class))),
            @ApiResponse(responseCode = "404", description = "The room searched was not found")
    })
    @PutMapping("/increase/{elementId}")
    public ResponseEntity<String> increaseElementQuantity(@PathVariable String elementId, int loanNumber){
        elementsService.increaseElementQuantity(elementId, loanNumber);
        return ResponseEntity.ok("The capacity was successfully increased");
    }
}
