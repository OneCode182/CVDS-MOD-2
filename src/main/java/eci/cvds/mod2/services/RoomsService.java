package eci.cvds.mod2.services;

import eci.cvds.mod2.modules.Room;
import eci.cvds.mod2.reposistories.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomsService {
    private RoomRepo roomRepo;

    @Autowired
    public RoomsService(RoomRepo roomRepo) {
        this.roomRepo = roomRepo;
    }

    public Optional<Room> getRoomById(String roomId) {
        return roomRepo.findById(roomId);
    }

    public List<Room> getRoomsByBuilding(String building) {
        return roomRepo.findByBuilding(building);
    }

    public List<Room> getRoomByCapacity(int capacity) {
        return roomRepo.findByCapacity(capacity);
    }

    public Room createRoom(Room room) {
        try {
            Room savedRoom = roomRepo.save(room);
            return savedRoom;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean deleteRoom(String roomId) {
        try {
            roomRepo.deleteById(roomId);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Room updateRoom(String roomId, Room newRoom) {
        if (!roomRepo.existsById(roomId)) {
            return null;
        }
        Room updatedRoom = roomRepo.findById(roomId).orElse(null);
        if (updatedRoom == null) {
            return null;
        }
        updatedRoom.setBuilding(newRoom.getBuilding());
        updatedRoom.setCapacity(newRoom.getCapacity());
        updatedRoom.setRoomId(newRoom.getRoomId());
        return roomRepo.save(updatedRoom);
    }

    public List<Room> getAll() {
        return roomRepo.findAll();
    }
}
