package eci.cvds.mod2.services;

import eci.cvds.mod2.exceptions.*;
import eci.cvds.mod2.modules.RecreationalElement;
import eci.cvds.mod2.modules.Room;
import eci.cvds.mod2.reposistories.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomsService {
    private final RoomRepo roomRepo;
    private final ElementsService elementsService;
    @Autowired
    public RoomsService(RoomRepo roomRepo, ElementsService elementsService){
        this.roomRepo=roomRepo;
        this.elementsService = elementsService;

    }

    public Room getRoomByRoomId(String roomId) {
        return roomRepo.findByRoomId(roomId)
                .orElseThrow(()->new RoomNotFoundException(RoomException.ROOM_NOT_FOUND));
    }

    public List<Room> getRoomsByBuilding(char building) {

        return roomRepo.findByBuilding(building);
    }

    public List<Room> getRoomByCapacity(int capacity) {
        return roomRepo.findByCapacity(capacity);
    }

    public Room createRoom(Room room) {
        if (roomRepo.findByRoomId(room.getRoomId()).isPresent()) {
            throw new RoomAlreadyExistException(RoomException.ROOM_ALREADY_EXIST);
        }
        return roomRepo.save(room);
    }

    public Room deleteRoom(String roomId) {
        Room room = roomRepo.findById(roomId)
                .orElseThrow(()-> new RoomNotFoundException(RoomException.ROOM_NOT_FOUND));
        roomRepo.deleteById(roomId);
        return room;
    }

    public Room updateRoom(String roomId, Room newRoom) {
        Room room = roomRepo.findByRoomId(roomId)
                .orElseThrow(()-> new RoomNotFoundException(RoomException.ROOM_NOT_FOUND));
        room.setRoomId(newRoom.getRoomId());
        room.setBuilding(newRoom.getBuilding());
        room.setElementList(newRoom.getElementList());
        return roomRepo.save(room);

    }


    public void addElementToRoom(String roomId, String elementId){
        elementsService.getElementById(elementId);
        Room room = this.getRoomByRoomId(roomId);
        room.addElement(elementId);
        roomRepo.save(room);
    }

    public void removeElementFromRoom(String roomId, String elementId){
        elementsService.getElementById(elementId);
        Room room = this.getRoomByRoomId(roomId);
        room.removeElement(elementId);
        roomRepo.save(room);
    }

    public List<Room> getAll() {
        return roomRepo.findAll();
    }
    public void reduceCapacityOfRoom(String roomId, int people){
        Room room = roomRepo.findByRoomId(roomId)
                .orElseThrow(()-> new RoomNotFoundException(RoomException.ROOM_NOT_FOUND));
        room.reduceCapacity(people);
        roomRepo.save(room);
    }
    public void increaseCapacityOfRoom(String roomId, int people){
        Room room = roomRepo.findByRoomId(roomId)
                .orElseThrow(()-> new RoomNotFoundException(RoomException.ROOM_NOT_FOUND));
        room.increaseCapacity(people);
        roomRepo.save(room);
    }
}
