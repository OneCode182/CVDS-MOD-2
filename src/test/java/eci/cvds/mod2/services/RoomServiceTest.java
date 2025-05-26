package eci.cvds.mod2.services;

import eci.cvds.mod2.exceptions.RoomAlreadyExistException;
import eci.cvds.mod2.exceptions.RoomNotFoundException;
import eci.cvds.mod2.modules.RecreationalElement;
import eci.cvds.mod2.modules.Room;
import eci.cvds.mod2.reposistories.RoomRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomsServiceTest {

    @Mock
    private RoomRepo roomRepo;

    @Mock
    private ElementsService elementsService;

    @InjectMocks
    private RoomsService roomsService;

    private Room room;

    @BeforeEach
    void setUp() {
        room = new Room();
        room.setRoomId("R101");
        room.setBuilding('A');
        room.setCapacity(10);
        room.setElementList(new HashSet<>(Set.of("e1")));
    }

    @Test
    void shouldReturnRoomByRoomId() {
        when(roomRepo.findByRoomId("R101")).thenReturn(Optional.of(room));

        Room result = roomsService.getRoomByRoomId("R101");

        assertEquals("R101", result.getRoomId());
        verify(roomRepo).findByRoomId("R101");
    }

    @Test
    void shouldThrowWhenRoomIdNotFound() {
        when(roomRepo.findByRoomId("XYZ")).thenReturn(Optional.empty());

        assertThrows(RoomNotFoundException.class, () -> roomsService.getRoomByRoomId("XYZ"));
    }

    @Test
    void shouldReturnRoomsByBuilding() {
        List<Room> rooms = List.of(room);
        when(roomRepo.findByBuilding('A')).thenReturn(rooms);

        List<Room> result = roomsService.getRoomsByBuilding('A');

        assertEquals(1, result.size());
        verify(roomRepo).findByBuilding('A');
    }

    @Test
    void shouldReturnRoomsByCapacity() {
        List<Room> rooms = List.of(room);
        when(roomRepo.findByCapacity(10)).thenReturn(rooms);

        List<Room> result = roomsService.getRoomByCapacity(10);

        assertEquals(1, result.size());
        verify(roomRepo).findByCapacity(10);
    }

    @Test
    void shouldCreateRoomSuccessfully() {
        when(roomRepo.findByRoomId("R101")).thenReturn(Optional.empty());
        when(roomRepo.save(room)).thenReturn(room);

        Room result = roomsService.createRoom(room);

        assertEquals("R101", result.getRoomId());
        verify(roomRepo).save(room);
    }

    @Test
    void shouldNotCreateRoomIfAlreadyExists() {
        when(roomRepo.findByRoomId("R101")).thenReturn(Optional.of(room));

        assertThrows(RoomAlreadyExistException.class, () -> roomsService.createRoom(room));
    }

    @Test
    void shouldDeleteRoomSuccessfully() {
        when(roomRepo.findById("1")).thenReturn(Optional.of(room));

        Room result = roomsService.deleteRoom("1");

        assertEquals("R101", result.getRoomId());
        verify(roomRepo).deleteById("1");
    }

    @Test
    void shouldUpdateRoomSuccessfully() {
        Room newRoom = new Room();
        newRoom.setRoomId("R202");
        newRoom.setBuilding('B');
        newRoom.setElementList(Set.of("e1"));

        when(roomRepo.findByRoomId("R101")).thenReturn(Optional.of(room));
        when(roomRepo.save(any())).thenReturn(room);

        Room result = roomsService.updateRoom("R101", newRoom);

        assertEquals("R202", result.getRoomId());
        verify(roomRepo).save(room);
    }

    @Test
    void shouldAddElementToRoom() {
        when(elementsService.getElementById("e2")).thenReturn(null);
        when(roomRepo.findByRoomId("R101")).thenReturn(Optional.of(room));
        when(roomRepo.save(room)).thenReturn(room);

        roomsService.addElementToRoom("R101", "e2");

        assertTrue(room.getElementList().contains("e2"));
        verify(roomRepo).save(room);
    }

    @Test
    void shouldRemoveElementFromRoom() {
        when(elementsService.getElementById("e1")).thenReturn(null);
        when(roomRepo.findByRoomId("R101")).thenReturn(Optional.of(room));
        when(roomRepo.save(room)).thenReturn(room);

        roomsService.removeElementFromRoom("R101", "e1");

        assertFalse(room.getElementList().contains("e1"));
        verify(roomRepo).save(room);
    }

    @Test
    void shouldReturnAllRooms() {
        when(roomRepo.findAll()).thenReturn(List.of(room));

        List<Room> result = roomsService.getAll();

        assertEquals(1, result.size());
        verify(roomRepo).findAll();
    }

    @Test
    void shouldReduceRoomCapacity() {
        when(roomRepo.findByRoomId("R101")).thenReturn(Optional.of(room));
        roomsService.reduceCapacityOfRoom("R101", 2);
        verify(roomRepo).save(room);
    }

    @Test
    void shouldIncreaseRoomCapacity() {
        when(roomRepo.findByRoomId("R101")).thenReturn(Optional.of(room));
        roomsService.increaseCapacityOfRoom("R101", 3);
        verify(roomRepo).save(room);
    }
}
