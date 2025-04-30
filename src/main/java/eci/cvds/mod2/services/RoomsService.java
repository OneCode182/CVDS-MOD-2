package eci.cvds.mod2.services;

import eci.cvds.mod2.modules.Room;
import eci.cvds.mod2.reposistories.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomsService {
    private RoomRepo roomRepo;
    @Autowired
    public RoomsService(RoomRepo roomRepo){
        this.roomRepo=roomRepo;
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

    public Room deleteRoom(String roomId) {
        return null;
    }

    public Room updateRoom(String roomId, Room newRoom) {
        return null;
    }

    public List<Room> getAll() {
        return null;
    }
}
