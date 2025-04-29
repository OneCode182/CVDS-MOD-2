package eci.cvds.mod2.controllers;

import eci.cvds.mod2.modules.Room;
import eci.cvds.mod2.services.RoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rooms")
@CrossOrigin(origins = "*")
public class RoomController {

    RoomsService roomsService;
    @Autowired
    public RoomController(RoomsService roomsService){
        this.roomsService = roomsService;
    }
    public Room getRoomById(String roomId) {
        return null;
    }

    public List<Room> getRoomsByBuilding(char building) {
        return null;
    }

    public List<Room> getRoomByCapacity(int capacity) {
        return null;
    }

    public Room createRoom(Room room) {
        return null;
    }

    public Room deleteRoom(Room room) {
        return null;
    }

    public Room updateRoom(Room room, Room newRoom) {
        return null;
    }

    public List<Room> getAll() {
        return null;
    }
}
