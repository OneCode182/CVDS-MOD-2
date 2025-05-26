package eci.cvds.mod2.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import eci.cvds.mod2.exceptions.*;
import eci.cvds.mod2.modules.Room;
import eci.cvds.mod2.services.RoomsService;
import eci.cvds.mod2.util.GlobalExceptionHandler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class RoomControllerTest {
    @InjectMocks
    RoomController roomController;
    @Mock
    RoomsService roomsService;

    private MockMvc mockMvc;

    Room room;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        room = new Room("1",'C',2,new HashSet<>());
        mockMvc = MockMvcBuilders
                .standaloneSetup(roomController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }
    @Test
    void shouldReturn200WhenGettingRoomWithId() throws  Exception{
        when(roomsService.getRoomByRoomId(room.getRoomId())).thenReturn(room);
        mockMvc.perform(get("/rooms/id/"+room.getRoomId())).andExpect(status().isOk());
    }
    @Test
    void shouldReturn404WhenGettingNonExistingRoomWithId() throws Exception{
        when(roomsService.getRoomByRoomId(room.getRoomId()))
                .thenThrow(new RoomNotFoundException(RoomException.ROOM_NOT_FOUND));
        mockMvc.perform(get("/rooms/id/"+room.getRoomId())).andExpect(status().isNotFound());
    }
    @Test
    void shouldReturn200WhenGettingRoomWithBuilding() throws  Exception{
        List<Room> rooms = Collections.singletonList(room);
        when(roomsService.getRoomsByBuilding(room.getBuilding())).thenReturn(rooms);
        mockMvc.perform(get("/rooms/building/"+room.getBuilding())).andExpect(status().isOk());
    }
    @Test
    void shouldReturn200WhenGettingRoomWithCapacity() throws  Exception{
        List<Room> rooms = Collections.singletonList(room);
        when(roomsService.getRoomByCapacity(room.getCapacity())).thenReturn(rooms);
        mockMvc.perform(get("/rooms/capacity/"+room.getCapacity())).andExpect(status().isOk());
    }
    @Test
    void shouldReturn201WhenCorrectCreationOfRoom() throws Exception {
        Room room1 = mock(Room.class);
        when(roomsService.createRoom(any(Room.class))).thenReturn(room1);
        mockMvc.perform(post("/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(room1)))
                .andExpect(status().isCreated());
        verify(roomsService).createRoom(any(Room.class));
    }
    @Test
    void shouldReturn400WhenCorrectCreationOfRoomThatAlreadyExist() throws Exception {
        Room room1 = mock(Room.class);
        when(roomsService.createRoom(any(Room.class))).thenThrow(new RoomAlreadyExistException(RoomException.ROOM_ALREADY_EXIST));
        mockMvc.perform(post("/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(room1)))
                .andExpect(status().isBadRequest());
        verify(roomsService).createRoom(any(Room.class));
    }
    @Test
    void shouldReturn200WhenDeletingRoom() throws Exception {
        when(roomsService.deleteRoom(room.getRoomId())).thenReturn(room);
        mockMvc.perform(delete("/rooms/"+room.getRoomId()))
                .andExpect(status().isOk());
        verify(roomsService).deleteRoom(room.getRoomId());
    }
    @Test
    void shouldReturn400WhenDeletingNonExistentRoom() throws Exception {
        when(roomsService.deleteRoom(room.getRoomId()))
                .thenThrow(new RoomNotFoundException(RoomException.ROOM_NOT_FOUND));
        mockMvc.perform(delete("/rooms/"+room.getRoomId()))
                .andExpect(status().isNotFound());
        verify(roomsService).deleteRoom(room.getRoomId());
    }
    @Test
    void shouldReturn200WhenUpdatingRoom() throws Exception {
        when (roomsService.updateRoom(eq(room.getRoomId()),any(Room.class))).thenReturn(room);
        mockMvc.perform(put("/rooms/"+room.getRoomId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(room)))
                .andExpect(status().isOk());
        verify(roomsService).updateRoom(eq("1"),any(Room.class));
    }
    @Test
    void shouldReturn404WhenUpdatingNotExistentRoom() throws Exception {
        when(roomsService.updateRoom(eq(room.getRoomId()),any(Room.class)))
                .thenThrow(new RoomNotFoundException(RoomException.ROOM_NOT_FOUND));
        mockMvc.perform(put("/rooms/"+room.getRoomId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(room)))
                .andExpect(status().isNotFound());
        verify(roomsService).updateRoom(eq(room.getRoomId()),any(Room.class));
    }
    @Test
    void shouldReturn200WhenGettingAllElements() throws Exception{
        List<Room> rooms = Collections.singletonList(room);
        when(roomsService.getAll()).thenReturn(rooms);
        mockMvc.perform(get("/rooms")).andExpect(status().isOk());
    }
    @Test
    void shouldReturn200WhenAddingElementToRoom() throws Exception{
        doNothing().when(roomsService).addElementToRoom(room.getRoomId(), "111");
        mockMvc.perform(post("/rooms/add/"+room.getRoomId()+"/111"))
                .andExpect(status().isOk())
                .andExpect(content().string("Element added successfully"));
    }
    @Test
    void shouldReturn404WhenAddingElementToNonExistentRoom() throws Exception{
        doThrow(new RoomNotFoundException(RoomException.ROOM_NOT_FOUND)).
                when(roomsService).addElementToRoom(room.getRoomId(),"111");
        mockMvc.perform(post("/rooms/add/"+room.getRoomId()+"/111"))
                .andExpect(status().isNotFound());
    }
    @Test
    void shouldReturn404WhenAddingNonExistingElementToRoom() throws Exception{
        doThrow(new ElementNotFoundException(ElementException.ELEMENT_NOT_FOUND)).
                when(roomsService).addElementToRoom(room.getRoomId(),"111");
        mockMvc.perform(post("/rooms/add/"+room.getRoomId()+"/111"))
                .andExpect(status().isNotFound());
    }
    @Test
    void shouldReturn200WhenRemovingElementToRoom() throws Exception{
        doNothing().when(roomsService).removeElementFromRoom(room.getRoomId(), "111");
        mockMvc.perform(post("/rooms/remove/"+room.getRoomId()+"/111"))
                .andExpect(status().isOk())
                .andExpect(content().string("Element removed successfully"));
    }
    @Test
    void shouldReturn404WhenRemovingElementToNonExistentRoom() throws Exception{
        doThrow(new RoomNotFoundException(RoomException.ROOM_NOT_FOUND)).
                when(roomsService).removeElementFromRoom(room.getRoomId(),"111");
        mockMvc.perform(post("/rooms/remove/"+room.getRoomId()+"/111"))
                .andExpect(status().isNotFound());
    }
    @Test
    void shouldReturn404WhenRemovingNonExistingElementToRoom() throws Exception{
        doThrow(new ElementNotFoundException(ElementException.ELEMENT_NOT_FOUND)).
                when(roomsService).removeElementFromRoom(room.getRoomId(),"111");
        mockMvc.perform(post("/rooms/remove/"+room.getRoomId()+"/111"))
                .andExpect(status().isNotFound());
    }


}