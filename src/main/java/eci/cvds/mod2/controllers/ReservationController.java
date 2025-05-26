package eci.cvds.mod2.controllers;

import eci.cvds.mod2.modules.RecreationalElement;
import eci.cvds.mod2.modules.Reservation;
import eci.cvds.mod2.services.ReservationService;
import eci.cvds.mod2.util.Date;
import eci.cvds.mod2.util.Role;
import eci.cvds.mod2.util.State;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/revs")
@CrossOrigin(origins = "*")
@Tag(name = "Reservas de Salas", description = "Operaciones relacionadas con la reserva de salas")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Operation(
            summary = "Obtener Reservas por ID",
            description = "Retorna una reserva dado su identificador.",
            parameters = {
                    @Parameter(name = "userId", description = "ID de la reserva", required = true, example = "642b7f1d0001fae3d4")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Elementos encontrados",
                            content = @Content(schema = @Schema(implementation = Reservation.class))),
                    @ApiResponse(responseCode = "404", description = "There are no reservations under that user id")
            }
    )
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Reservation>> getReservationsByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(reservationService.getReservationsByUserId(userId));
    }

    @Operation(
            summary = "Obtener reserva por ID",
            description = "Retorna una reserva dado su identificador.",
            parameters = {
                    @Parameter(name = "revId", description = "ID de la reserva", required = true, example = "642b7f1d0001fae3d4")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Elemento encontrado",
                            content = @Content(schema = @Schema(implementation = Reservation.class))),
                    @ApiResponse(responseCode = "404", description = "Reservation not found")
            }
    )
    @GetMapping("/id/{revId}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable String revId) {
        return ResponseEntity.ok(reservationService.getReservationById(revId));
    }


    @Operation(
            summary = "Obtener reservas por fecha",
            description = "Retorna reservas dado una fecha.",
            parameters = {
                    @Parameter(name = "date", description = "Fecha de la reserva", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Elementos encontrados",
                            content = @Content(schema = @Schema(implementation = Reservation.class))),
                    @ApiResponse(responseCode = "404", description = "There are no reservations the requested day and hour")
            }
    )
    @PostMapping("/date")
    public ResponseEntity<List<Reservation>> getReservationsByDay(@RequestBody Date date) {
        return ResponseEntity.ok(reservationService.getReservationsByDay(date));
    }

    @Operation(
            summary = "Obtener reservas por ID de sala",
            description = "Retorna reservas dado un ID de sala.",
            parameters = {
                    @Parameter(name = "roomId", description = "ID de la sala", required = true, example = "642b7f1d0001fae3d4")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Elementos encontrados",
                            content = @Content(schema = @Schema(implementation = Reservation.class))),
                    @ApiResponse(responseCode = "404", description = "There are no reservations in that room")
            }
    )
    @GetMapping("/room/{roomId}")
    public List<Reservation> getReservationsByRoom(@PathVariable String roomId) {
        return reservationService.getReservationsByRoom(roomId);
    }

    @Operation(
            summary = "Obtener reservas por estado",
            description = "Retorna reservas dado un estado.",
            parameters = {
                    @Parameter(name = "state", description = "Estado de la reserva", required = true, example = "RESERVA_CONFIRMADA")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Elementos encontrados",
                            content = @Content(schema = @Schema(implementation = Reservation.class))),
                    @ApiResponse(responseCode = "404", description = "There are no reservations with that state")
            }
    )
    @GetMapping("/state/{state}")
    public List<Reservation> getReservationsByState(@PathVariable State state) {
        return reservationService.getReservationsByState(state);
    }

    @Operation(
            summary = "Crear una nueva reserva",
            description = "Crea una nueva reserva con la información proporcionada.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Objeto de la nueva reserva a crear",
                    required = true,
                    content = @Content(schema = @Schema(implementation = Reservation.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201"),
                    @ApiResponse(responseCode = "400", description = "That reservation has been booked under that id, in the same room at the same time"),
                    @ApiResponse(responseCode = "404", description = "The room searched was not found")
            }
    )
    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation rev) {
        Reservation created = reservationService.createReservation(rev);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(
            summary = "Actualizar una reserva",
            description = "Actualiza una reserva existente con la información proporcionada.",
            parameters = {
                    @Parameter(name = "revId", description = "ID de la reserva", required = true, example = "642b7f1d0001fae3d4")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Objeto de la reserva a actualizar",
                    required = true,
                    content = @Content(schema = @Schema(implementation = Reservation.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reservation successfully updated"),
                    @ApiResponse(responseCode = "404", description = "Reservation not found"),
                    @ApiResponse(responseCode = "404", description = "The room searched was not found")
            }
    )
    @PutMapping("/{revId}")
    public ResponseEntity<String> updateReservation(@PathVariable String revId, @RequestBody Reservation newRev) {
        reservationService.updateReservation(revId, newRev);
        return ResponseEntity.ok("Reservation successfully updated");
    }

    @Operation(
            summary = "Eliminar una reserva",
            description = "Elimina una reserva dado su ID.",
            parameters = {
                    @Parameter(name = "revId", description = "ID de la reserva", required = true, example = "642b7f1d0001fae3d4")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reservation successfully deleted"),
                    @ApiResponse(responseCode = "404", description = "Reservation not found"),
                    @ApiResponse(responseCode = "404", description = "The room searched was not found")
            }
    )
    @DeleteMapping("/{revId}")
    public ResponseEntity<String> deleteReservation(@PathVariable String revId) {
        reservationService.deleteReservation(revId);
        return ResponseEntity.ok("Reservation successfully deleted");
    }

    @Operation(
            summary = "Cambiar el estado de una reserva",
            description = "Cambia el estado de una reserva dado su ID.",
            parameters = {
                    @Parameter(name = "revId", description = "ID de la reserva", required = true, example = "642b7f1d0001fae3d4"),
                    @Parameter(name = "state", description = "Nuevo estado de la reserva", required = true, example = "RESERVA_CANCELADA")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "The state was successfully changed"),
                    @ApiResponse(responseCode = "404", description = "Reservation not found"),
                    @ApiResponse(responseCode = "400", description = "Bad Request")
            }
    )
    @PutMapping("/state/{revId}/{state}")
    public ResponseEntity<String> changeReservationState(@PathVariable String revId, @PathVariable State state){
        reservationService.changeReservationState(revId,state);
        return ResponseEntity.ok("The state was successfully changed");
    }

    @Operation(
            summary = "Obtener todas las reservas",
            description = "Retorna todas las reservas.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Elementos encontrados",
                            content = @Content(schema = @Schema(implementation = Reservation.class)))
            }
    )
    @GetMapping
    public List<Reservation> getAll() {
        return reservationService.getAll();
    }
    @GetMapping("/user")
    public List<Reservation> getReservationsByTokenUser(){
        return reservationService.getReservationByTokenUser();
    }
}
