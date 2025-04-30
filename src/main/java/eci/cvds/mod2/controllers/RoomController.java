package eci.cvds.mod2.controllers;

import eci.cvds.mod2.modules.Room;
import eci.cvds.mod2.services.RoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        Room room = roomsService.getRoomById(roomId);
        return ResponseEntity.ok(room);
    }

    @GetMapping("/building/{building}")
    public List<Room> getRoomsByBuilding(@PathVariable char building) {
        return roomsService.getRoomsByBuilding(building);
    }

    @GetMapping("/capacity/{capacity}")
    public ResponseEntity<List<Room>> getRoomByCapacity(@PathVariable int capacity) {
        List<Room> rooms = roomsService.getRoomByCapacity(capacity);
        return ResponseEntity.ok(rooms);
    }

    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        Room created = roomsService.createRoom(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<String> deleteRoom(@PathVariable String roomId) {
        roomsService.deleteRoom(roomId);
        return ResponseEntity.ok("Room successfully deleted");
    }

    @PutMapping("/{roomId}")
    public ResponseEntity<Room> updateRoom(@PathVariable String roomId, @RequestBody Room newRoom) {
        Room updated = roomsService.updateRoom(roomId, newRoom);
        return ResponseEntity.ok(updated);
    }

    @GetMapping
    public ResponseEntity<List<Room>> getAll() {
        List<Room> rooms = roomsService.getAll();
        return ResponseEntity.ok(rooms);
    }
}

