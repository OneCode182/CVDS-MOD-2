package eci.cvds.mod2.controllers;

import eci.cvds.mod2.exceptions.RoomException;
import eci.cvds.mod2.exceptions.RoomNotFoundException;
import eci.cvds.mod2.modules.Loan;
import eci.cvds.mod2.modules.Room;
import eci.cvds.mod2.services.RoomsService;
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
@RequestMapping("/rooms")
@CrossOrigin(origins = "*")
@Tag(name = "Salas", description = "Operaciones relacionadas con las Salas de Bienestar Universitario")
public class RoomController {

    private final RoomsService roomsService;

    @Autowired
    public RoomController(RoomsService roomsService) {
        this.roomsService = roomsService;
    }


    @Operation(summary = "Obtener una sala por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sala encontrada",
                    content = @Content(schema = @Schema(implementation = Room.class))),
            @ApiResponse(responseCode = "404", description = "The room searched was not found")
    })
    @GetMapping("/id/{roomId}")
    public ResponseEntity<Room> getRoomById(@PathVariable String roomId) {
        Room room = roomsService.getRoomByRoomId(roomId);
        return ResponseEntity.ok(room);
    }


    @Operation(summary = "Obtener todas las salas con un edificio específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de salas por edificio",
                    content = @Content(schema = @Schema(implementation = Room.class)))
    })
    @GetMapping("/building/{building}")
    public List<Room> getRoomsByBuilding(@PathVariable char building) {
        return roomsService.getRoomsByBuilding(building);
    }


    @Operation(summary = "Obtener todas las salas con una capacidad específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de salas por capacidad",
                    content = @Content(schema = @Schema(implementation = Room.class)))
    })
    @GetMapping("/capacity/{capacity}")
    public ResponseEntity<List<Room>> getRoomByCapacity(@PathVariable int capacity) {
        List<Room> rooms = roomsService.getRoomByCapacity(capacity);
        return ResponseEntity.ok(rooms);
    }


    @Operation(
            summary = "Crear una nueva sala",
            description = "Crea una nueva sala con la información proporcionada.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Objeto de la nueva sala a crear",
                    required = true,
                    content = @Content(schema = @Schema(implementation = Room.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Room successfully created"),
                    @ApiResponse(responseCode = "400", description = "The room you tried to create already exists")
            }
    )
    @PostMapping
    public ResponseEntity<Room> createRoom(@Valid @RequestBody Room room) {
        Room created = roomsService.createRoom(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(
            summary = "Eliminar una sala",
            description = "Elimina una sala dado su ID.",
            parameters = {
                    @Parameter(name = "roomId", description = "ID de la sala", required = true, example = "642b7fsdfsd0001fae3d4")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Room successfully deleted"),
                    @ApiResponse(responseCode = "404", description = "The room searched was not found")
            }
    )
    @DeleteMapping("/{roomId}")
    public ResponseEntity<String> deleteRoom(@PathVariable String roomId) {
        roomsService.deleteRoom(roomId);
        return ResponseEntity.ok("Room successfully deleted");
    }


    @Operation(
            summary = "Actualizar una sala",
            description = "Actualiza una sala existente con la información proporcionada.",
            parameters = {
                    @Parameter(name = "roomId", description = "ID de la sala", required = true, example = "642b7fsdfsd0001fae3d4")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Objeto de la sala a actualizar",
                    required = true,
                    content = @Content(schema = @Schema(implementation = Room.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Room successfully updated"),
                    @ApiResponse(responseCode = "404", description = "The room searched was not found")
            }
    )
    @PutMapping("/{roomId}")
    public ResponseEntity<Room> updateRoom(@PathVariable String roomId, @Valid @RequestBody Room newRoom) {
        Room updated = roomsService.updateRoom(roomId, newRoom);
        return ResponseEntity.ok(updated);
    }


    @Operation(summary = "Obtener todas las salas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de todas las salas",
                    content = @Content(schema = @Schema(implementation = Room.class)))
    })
    @GetMapping
    public ResponseEntity<List<Room>> getAll() {
        List<Room> rooms = roomsService.getAll();
        return ResponseEntity.ok(rooms);
    }

    @Operation(summary = "Agregar un elemento a una sala")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element added successfully",
                    content = @Content(schema = @Schema(implementation = Room.class))),
            @ApiResponse(responseCode = "404", description = "The room searched was not found"),
            @ApiResponse(responseCode = "404", description = "The Element searched was not found")
    })
    @PostMapping("/add/{roomId}/{elementId}")
    public ResponseEntity<String> addElementToRoom(@PathVariable String roomId,@PathVariable String elementId){
        roomsService.addElementToRoom(roomId,elementId);
        return ResponseEntity.ok("Element added successfully");
    }


    @Operation(summary = "Eliminar un elemento de una sala")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element removed successfully",
                    content = @Content(schema = @Schema(implementation = Room.class))),
            @ApiResponse(responseCode = "404", description = "The room searched was not found"),
            @ApiResponse(responseCode = "404", description = "The Element searched was not found")
    })
    @PostMapping("/remove/{roomId}/{elementId}")
    public ResponseEntity<String> removeElementFromRoom(@PathVariable String roomId,@PathVariable String elementId){
        roomsService.removeElementFromRoom(roomId,elementId);
        return ResponseEntity.ok("Element removed successfully");
    }
}

