package eci.cvds.mod2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import eci.cvds.mod2.controllers.RoomController;
import eci.cvds.mod2.modules.Room;
import eci.cvds.mod2.services.RoomsService;
import io.swagger.v3.oas.models.media.MediaType;


@SpringBootTest
public class RoomTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private RoomsService roomsService;

    @InjectMocks
    private RoomController roomController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(roomController).build();
    }

    @Test
    void shouldReturn200WhenGettingAllRooms() throws Exception {
        List<Room> rooms = Arrays.asList(
                new Room("123", "BuildingA", 23),
                new Room("456", "BuildingB", 32)
        );

        when(roomsService.getAll()).thenReturn(rooms);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn200WhenGettingRoomById() throws Exception {
        Room room = new Room("123", "BuildingA", 23);
        when(roomsService.getRoomById("R1")).thenReturn(Optional.of(room));

        mockMvc.perform(get("/id/R1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn404WhenRoomIdNotFound() throws Exception {
        when(roomsService.getRoomById("9999")).thenReturn(Optional.empty());

        mockMvc.perform(get("/id/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn200WhenGettingRoomsByBuilding() throws Exception {
        List<Room> rooms = List.of(new Room("123", "BuildingA", 23));
        when(roomsService.getRoomsByBuilding("BuildingA")).thenReturn(rooms);

        mockMvc.perform(get("/building/BuildingA"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn200WhenGettingRoomsByCapacity() throws Exception {
        List<Room> rooms = List.of(new Room("123", "BuildingA", 23));
        when(roomsService.getRoomByCapacity(23)).thenReturn(rooms);

        mockMvc.perform(get("/capacity/23"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn201WhenCreatingRoom() throws Exception {
        Room room = new Room("123", "BuildingA", 23);
        when(roomsService.createRoom(any(Room.class))).thenReturn(room);

        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(room)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturn500WhenRoomCreationFails() throws Exception {
        Room room = new Room("123", "BuildingA", 23);
        when(roomsService.createRoom(any(Room.class))).thenReturn(null);

        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(room)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void shouldReturn200WhenUpdatingRoom() throws Exception {
        Room updatedRoom = new Room("123", "BuildingA", 23);
        when(roomsService.updateRoom(eq("123"), any(Room.class))).thenReturn(updatedRoom);

        mockMvc.perform(put("/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRoom)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn500WhenUpdateFails() throws Exception {
        Room room = new Room("123", "BuildingA", 23);
        when(roomsService.updateRoom(eq("123"), any(Room.class))).thenReturn(null);

        mockMvc.perform(put("/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(room)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void shouldReturn200WhenDeletingRoom() throws Exception {
        when(roomsService.deleteRoom("123")).thenReturn(true);

        mockMvc.perform(delete("/123"))
                .andExpect(status().isOk())
                .andExpect(content().string("Room successfully deleted"));
    }

    @Test
    void shouldReturn500WhenDeletingRoomFails() throws Exception {
        when(roomsService.deleteRoom("123")).thenReturn(false);

        mockMvc.perform(delete("/123"))
                .andExpect(status().isInternalServerError());
    }
}

