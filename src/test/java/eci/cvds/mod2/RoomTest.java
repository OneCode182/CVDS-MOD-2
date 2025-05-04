package eci.cvds.mod2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import eci.cvds.mod2.controllers.RoomController;
import eci.cvds.mod2.modules.Room;
import eci.cvds.mod2.services.RoomsService;


@SpringBootTest
public class RoomTest {

    @Mock
    private RoomsService roomsService;

    @InjectMocks
    private RoomController roomController;

    @Test
    void testGetRoomById() {
        String roomId = "R101";
        Room room = new Room(roomId,"A", 23);
        when(roomsService.getRoomById(roomId).get()).thenReturn(room);
        ResponseEntity<Room> response = roomController.getRoomById(roomId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(room, response.getBody());
    }

    @Test
    void testGetRoomById_NotFound() {
        String roomId = "R999";
        Room room = new Room(roomId,"A", 23);
        when(roomsService.getRoomById(roomId).get()).thenReturn(room);
        ResponseEntity<Room> response = roomController.getRoomById(roomId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetRoomsByBuilding() {
        String building = "BuildingA";
        Room room = new Room("23",building, 23);
        List<Room> roomList = List.of(room);
        when(roomsService.getRoomsByBuilding(building)).thenReturn(roomList);
        List<Room> response = roomController.getRoomsByBuilding(building);
        assertEquals(1, response.size());
        assertEquals(roomList, response);
    }

    @Test
    void testGetRoomByCapacity() {
        int capacity = 30;
        Room room = new Room("23","A", capacity);
        List<Room> roomList = List.of(room);
        when(roomsService.getRoomByCapacity(capacity)).thenReturn(roomList);
        List<Room> response = roomController.getRoomByCapacity(capacity);
        assertEquals(1, response.size());
        assertEquals(roomList, response);
    }

    @Test
    void testCreateRoom() {
        Room room = new Room("23","A", 23);
        when(roomsService.createRoom(room)).thenReturn(room);
        ResponseEntity<Room> response = roomController.createRoom(room);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(room, response.getBody());
    }

    @Test
    void testUpdateRoom() {
        String idRoom = "123";
        Room room = new Room(idRoom,"A", 23);
        when(roomsService.createRoom(room)).thenReturn(room);
        room.setCapacity(32);
        when(roomsService.updateRoom(idRoom, room)).thenReturn(room);
        ResponseEntity<Room> response = roomController.updateRoom(idRoom, room);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(room, response.getBody());
    }

    @Test
    void testDeleteRoom_Success() {
        String roomId = "R101";
        when(roomsService.deleteRoom(roomId)).thenReturn(true);
        ResponseEntity<String> response = roomController.deleteRoom(roomId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Room successfully deleted", response.getBody());
    }

    @Test
    void testDeleteRoom_Failure() {
        String roomId = "R101";
        when(roomsService.deleteRoom(roomId)).thenReturn(false);
        ResponseEntity<String> response = roomController.deleteRoom(roomId);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetAllRooms() {
        Room room1 = new Room("123","A", 23);
        Room room2 = new Room("456","B", 32);
        List<Room> roomList = List.of(room1,room2);
        when(roomsService.getAll()).thenReturn(roomList);
        List<Room> response = roomController.getAll();
        assertEquals(2, response.size());
        assertEquals(roomList, response);
    }
}

