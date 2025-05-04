package eci.cvds.mod2.controllers;

import eci.cvds.mod2.modules.Room;
import eci.cvds.mod2.services.RoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rooms")
@CrossOrigin(origins = "*")
public class RoomController {

    private final RoomsService roomsService;

    @Autowired
    public RoomController(RoomsService roomsService) {
        this.roomsService = roomsService;
    }

    @GetMapping("/id/{roomId}")
    public ResponseEntity<Room> getRoomById(@PathVariable String roomId) {
        Optional<Room> room = roomsService.getRoomById(roomId);
        if (room.isPresent()) {
            return ResponseEntity.ok(room.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/building/{building}")
    public List<Room> getRoomsByBuilding(@PathVariable String building) {
        return roomsService.getRoomsByBuilding(building);
    }

    @GetMapping("/capacity/{capacity}")
    public List<Room> getRoomByCapacity(@PathVariable int capacity) {
        return roomsService.getRoomByCapacity(capacity);
    }

    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        Room newRoom = roomsService.createRoom(room);
        if (newRoom != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(newRoom);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<String> deleteRoom(@PathVariable String roomId) {
        boolean deleted = roomsService.deleteRoom(roomId);
        if (deleted) {
            return ResponseEntity.ok().body("Room successfully deleted");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{roomId}")
    public ResponseEntity<Room> updateRoom(@PathVariable String roomId, @RequestBody Room newRoom) {
        Room updatedRoom = roomsService.updateRoom(roomId, newRoom);
        if (updatedRoom != null) {
            return ResponseEntity.ok(updatedRoom);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    @GetMapping
    public List<Room> getAll() {
        return roomsService.getAll();
    }
}
