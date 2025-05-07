package eci.cvds.mod2.controllers;

import eci.cvds.mod2.modules.Loan;
import eci.cvds.mod2.modules.RecreationalElement;
import eci.cvds.mod2.services.LoanService;
import eci.cvds.mod2.util.State;

import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/loans")
@Tag(name = "Prestamos", description = "Operaciones relacionadas con los prestamos de elementos recreativos")
public class LoanController {
    LoanService loanService;
    @Autowired
    public LoanController(LoanService loanService){
        this.loanService=loanService;
    }


    @Operation(summary = "Obtener un préstamo por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Préstamo encontrado",
                    content = @Content(schema = @Schema(implementation = Loan.class))),
            @ApiResponse(responseCode = "404", description = "The searched loan was not found")
    })
    @GetMapping("/id/{loanId}")
    public ResponseEntity<Loan> getLoanById(@PathVariable String loanId) {
        Loan loan = loanService.getLoanById(loanId);
        return ResponseEntity.ok(loan);
    }


    @Operation(summary = "Obtener todos los préstamos con un estado específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de préstamos por estado",
                    content = @Content(schema = @Schema(implementation = Loan.class)))
    })
    @GetMapping("/state/{state}")
    public List<Loan> getLoansByState(@PathVariable State state) {
        return loanService.getLoansByState(state);
    }


    @Operation(
            summary = "Crear un nuevo prestamo",
            description = "Crea un nuevo prestamo con la información proporcionada.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Objeto del nuevo prestamo a crear",
                    required = true,
                    content = @Content(schema = @Schema(implementation = Loan.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Loan successfully created")
            }
    )
    @PostMapping
    public ResponseEntity<String> createLoan(@Valid @RequestBody Loan loan) {
        loanService.createLoan(loan);
        return ResponseEntity.status(HttpStatus.CREATED).body("Loan successfully created");
    }

    @Operation(
            summary = "Actualizar un préstamo",
            description = "Actualiza un préstamo existente con la información proporcionada.",
            parameters = {
                    @Parameter(name = "loanId", description = "ID del préstamo", required = true, example = "642b7f1acb0a1d0001fae3d4")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Objeto del préstamo a actualizar",
                    required = true,
                    content = @Content(schema = @Schema(implementation = Loan.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Loan successfully updated"),
                    @ApiResponse(responseCode = "404", description = "The searched loan was not found")
            }
    )
    @PutMapping("/{loanId}")
    public ResponseEntity<String> updateLoan(@PathVariable String loanId, @Valid @RequestBody Loan newLoan) {
        loanService.updateLoan(loanId,newLoan);
        return ResponseEntity.ok("Loan successfully updated ");
    }


    @Operation(
            summary = "Eliminar un préstamo",
            description = "Elimina un préstamo dado su ID.",
            parameters = {
                    @Parameter(name = "loanId", description = "ID del préstamo", required = true, example = "642b7f1acb0a1d0001fae3d4")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Loan successfully deleted"),
                    @ApiResponse(responseCode = "404", description = "The searched loan was not found")
            }
    )
    @DeleteMapping("/{loanId}")
    public ResponseEntity<String> deleteLoan(@PathVariable String loanId) {
        loanService.deleteLoan(loanId);
        return ResponseEntity.ok("Loan successfully deleted");
    }


}
