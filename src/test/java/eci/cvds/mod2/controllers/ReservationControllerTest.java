package eci.cvds.mod2.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import eci.cvds.mod2.exceptions.*;
import eci.cvds.mod2.modules.Reservation;
import eci.cvds.mod2.modules.Room;
import eci.cvds.mod2.services.ReservationService;
import eci.cvds.mod2.util.Date;
import eci.cvds.mod2.util.GlobalExceptionHandler;
import eci.cvds.mod2.util.Role;
import eci.cvds.mod2.util.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

 class ReservationControllerTest {
    @InjectMocks
    ReservationController reservationController;
    @Mock
    ReservationService reservationService;

    MockMvc mockMvc;
    Reservation reservation;
    ObjectMapper objectMapper;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reservation = Reservation.builder().id("123")
                .userName("Santiago")
                .userId("2")
                .role(Role.SALA_ADMIN)
                .date(new Date(LocalDate.now(), LocalTime.now()))
                .roomId("1")
                .state(State.RESERVA_CONFIRMADA)
                .people(5)
                .build();
        mockMvc = MockMvcBuilders
                .standaloneSetup(reservationController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }
     @Test
     void shouldReturn200WhenGettingRevWithUserId() throws  Exception{
         List<Reservation> reservations = Collections.singletonList(reservation);
         when(reservationService.getReservationsByUserId(reservation.getUserId())).thenReturn(reservations);
         mockMvc.perform(get("/revs/user/"+reservation.getUserId()))
                 .andExpect(status().isOk());
     }
     @Test
     void shouldReturn404WhenGettingNonExistingRevWithUserId() throws Exception{
         when(reservationService.getReservationsByUserId("someId123"))
                 .thenThrow(new ReservationNotFoundException(ReservationException.USER_HAS_NO_RESERVATIONS));
         mockMvc.perform(get("/revs/user/someId123")).andExpect(status().isNotFound());
     }
    @Test
    void shouldReturn200WhenGettingRevWithId() throws  Exception{
        when(reservationService.getReservationById(reservation.getId())).thenReturn(reservation);
        mockMvc.perform(get("/revs/id/"+reservation.getId()))
                .andExpect(status().isOk());
    }
    @Test
    void shouldReturn404WhenGettingNonExistingRevWithId() throws Exception{
        when(reservationService.getReservationById("someId123"))
                .thenThrow(new ReservationNotFoundException(ReservationException.REV_NOT_FOUND));
        mockMvc.perform(get("/revs/id/someId123")).andExpect(status().isNotFound());
    }
     @Test
     void shouldReturn200WhenGettingRevWithUserState() throws  Exception{
         List<Reservation> reservations = Collections.singletonList(reservation);
         when(reservationService.getReservationsByState(State.RESERVA_CONFIRMADA)).thenReturn(reservations);
         mockMvc.perform(get("/revs/state/"+reservation.getState().toString()))
                 .andExpect(status().isOk());
     }
     @Test
     void shouldReturn404WhenGettingNonExistingRevWithState() throws Exception{
         when(reservationService.getReservationsByState(State.RESERVA_CANCELADA))
                 .thenThrow(new ReservationNotFoundException(ReservationException.NO_RESERVATION_WITH_THAT_STATE));
         mockMvc.perform(get("/revs/state/someId123")).andExpect(status().isBadRequest());
     }

     @Test
     void shouldReturn404WhenGettingNonExistingRevWithDate() throws Exception{
         when(reservationService.getReservationsByDay(reservation.getDate()))
                 .thenThrow( new ReservationNotFoundException(ReservationException.NO_RESERVATIONS_ON_SPECIFIC_DAY));
         mockMvc.perform(post("/revs/date")).andExpect(status().isBadRequest());
     }
     @Test
     void shouldReturn200WhenGettingRevWithRoomId() throws  Exception{
         List<Reservation> reservations = Collections.singletonList(reservation);
         when(reservationService.getReservationsByRoom(reservation.getRoomId())).thenReturn(reservations);
         mockMvc.perform(get("/revs/room/"+reservation.getRoomId()))
                 .andExpect(status().isOk());
     }
     @Test
     void shouldReturn404WhenGettingNonExistingRevWithRoomId() throws Exception{
         when(reservationService.getReservationsByRoom("someId123"))
                 .thenThrow(new ReservationNotFoundException(ReservationException.NO_RESERVATIONS_IN_SPECIFIC_ROOM));
         mockMvc.perform(get("/revs/room/someId123")).andExpect(status().isNotFound());
     }
     @Test
     void shouldReturn201WhenCorrectCreationOfReservation() throws Exception {
         Reservation rev = mock(Reservation.class);
         when(reservationService.createReservation(any(Reservation.class))).thenReturn(rev);
         mockMvc.perform(post("/revs")
                         .contentType(MediaType.APPLICATION_JSON)
                         .content(new ObjectMapper().writeValueAsString(rev)))
                 .andExpect(status().isCreated());
         verify(reservationService).createReservation(any(Reservation.class));
     }
     @Test
     void shouldReturn400WhenCorrectCreationOfReservationThatAlreadyExist() throws Exception {
         Reservation rev = mock(Reservation.class);
         when(reservationService.createReservation(any(Reservation.class))).thenThrow(new ReservationNotFoundException(ReservationException.REV_ALREADY_EXIST));
         mockMvc.perform(post("/revs")
                         .contentType(MediaType.APPLICATION_JSON)
                         .content(new ObjectMapper().writeValueAsString(rev)))
                 .andExpect(status().isNotFound());
         verify(reservationService).createReservation(any(Reservation.class));
     }
     @Test
     void shouldReturn200WhenUpdatingRev() throws Exception {
         when (reservationService.updateReservation(eq(reservation.getId()),any(Reservation.class))).thenReturn(reservation);
         mockMvc.perform(put("/revs/"+reservation.getId())
                         .contentType(MediaType.APPLICATION_JSON)
                         .content(objectMapper.writeValueAsString(reservation)))
                 .andExpect(status().isOk());
         verify(reservationService).updateReservation(eq(reservation.getId()),any(Reservation.class));
     }
     @Test
     void shouldReturn404WhenUpdatingNotExistentRev() throws Exception {
         when(reservationService.updateReservation(eq(reservation.getId()),any(Reservation.class)))
                 .thenThrow(new ReservationNotFoundException(ReservationException.REV_NOT_FOUND));
         mockMvc.perform(put("/revs/"+reservation.getId())
                         .contentType(MediaType.APPLICATION_JSON)
                         .content(objectMapper.writeValueAsString(reservation)))
                 .andExpect(status().isNotFound());
         verify(reservationService).updateReservation(eq(reservation.getId()),any(Reservation.class));
     }
     @Test
     void shouldReturn200WhenDeletingReservation() throws Exception {
         when(reservationService.deleteReservation(reservation.getId())).thenReturn(reservation);
         mockMvc.perform(delete("/revs/"+reservation.getId()))
                 .andExpect(status().isOk());
         verify(reservationService).deleteReservation(reservation.getId());
     }
     @Test
     void shouldReturn400WhenDeletingNonExistentReservation() throws Exception {
         when(reservationService.deleteReservation(reservation.getId()))
                 .thenThrow(new ReservationNotFoundException(ReservationException.REV_NOT_FOUND));
         mockMvc.perform(delete("/revs/"+reservation.getId()))
                 .andExpect(status().isNotFound());
         verify(reservationService).deleteReservation(reservation.getId());
     }
     @Test
     void shouldReturn200WhenGettingAllReservations() throws Exception{
         List<Reservation> reservations = Collections.singletonList(reservation);
         when(reservationService.getAll()).thenReturn(reservations);
         mockMvc.perform(get("/revs")).andExpect(status().isOk());
     }
     @Test
     void shouldReturn200WhenChangingRevState() throws Exception {
       doNothing().when(reservationService).changeReservationState(reservation.getId(), State.RESERVA_CONFIRMADA);
       mockMvc.perform(put("/revs/state/"+reservation.getId()+"/RESERVA_CONFIRMADA"))
               .andExpect(status().isOk())
               .andExpect(content().string("The state was successfully changed"));
     }
     @Test
     void shouldReturn404WhenChangingNonExistingRevState() throws Exception{
        doThrow(new ReservationNotFoundException(ReservationException.REV_NOT_FOUND))
                .when(reservationService).changeReservationState(reservation.getId(),State.RESERVA_CONFIRMADA);
         mockMvc.perform(put("/revs/state/"+reservation.getId()+"/RESERVA_CONFIRMADA"))
                 .andExpect(status().isNotFound());
     }
}
