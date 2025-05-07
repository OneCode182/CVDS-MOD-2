package eci.cvds.mod2.controllers;

import eci.cvds.mod2.exceptions.RoomException;
import eci.cvds.mod2.exceptions.RoomNotFoundException;
import eci.cvds.mod2.modules.Room;
import eci.cvds.mod2.services.RoomsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")

public class RoomController {

    private final RoomsService roomsService;

    @Autowired
    public RoomController(RoomsService roomsService) {
        this.roomsService = roomsService;
    }

    @GetMapping("/id/{roomId}")
    public ResponseEntity<Room> getRoomById(@PathVariable String roomId) {
        Room room = roomsService.getRoomByRoomId(roomId);
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
    public ResponseEntity<Room> createRoom(@Valid @RequestBody Room room) {
        Room created = roomsService.createRoom(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<String> deleteRoom(@PathVariable String roomId) {
        roomsService.deleteRoom(roomId);
        return ResponseEntity.ok("Room successfully deleted");
    }

    @PutMapping("/{roomId}")
    public ResponseEntity<Room> updateRoom(@PathVariable String roomId, @Valid @RequestBody Room newRoom) {
        Room updated = roomsService.updateRoom(roomId, newRoom);
        return ResponseEntity.ok(updated);
    }

    @GetMapping
    public ResponseEntity<List<Room>> getAll() {
        List<Room> rooms = roomsService.getAll();
        return ResponseEntity.ok(rooms);
    }
    @PostMapping("/add/{roomId}/{elementId}")
    public ResponseEntity<String> addElementToRoom(@PathVariable String roomId,@PathVariable String elementId){
        roomsService.addElementToRoom(roomId,elementId);
        return ResponseEntity.ok("Element added successfully");
    }
    @PostMapping("/remove/{roomId}/{elementId}")
    public ResponseEntity<String> removeElementFromRoom(@PathVariable String roomId,@PathVariable String elementId){
        roomsService.removeElementFromRoom(roomId,elementId);
        return ResponseEntity.ok("Element removed successfully");
    }
    @PutMapping("/reduce/{roomId}")
    public ResponseEntity<String> reduceCapacityOfRoom(@PathVariable String roomId, int people){
        roomsService.reduceCapacityOfRoom(roomId, people);
        return ResponseEntity.ok("The capacity was successfully reduced");
    }
    @PutMapping("/increase/{roomId}")
    public ResponseEntity<String> increaseCapacityOfRoom(@PathVariable String roomId, int people){
        roomsService.increaseCapacityOfRoom(roomId, people);
        return ResponseEntity.ok("The capacity was successfully increased");
    }
}

