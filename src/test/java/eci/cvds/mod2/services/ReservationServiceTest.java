package eci.cvds.mod2.services;

import eci.cvds.mod2.exceptions.ReservationNotFoundException;
import eci.cvds.mod2.modules.CustomUserDetails;
import eci.cvds.mod2.modules.Reservation;
import eci.cvds.mod2.reposistories.ReservationRepo;
import eci.cvds.mod2.util.Date;
import eci.cvds.mod2.util.Role;
import eci.cvds.mod2.util.State;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepo reservationRepo;

    @Mock
    private IEmailService emailService;

    @Mock
    private RoomsService roomsService;

    @InjectMocks
    private ReservationService reservationService;

    private Reservation reservation;

    @BeforeEach
    void setUp() {
        reservation = Reservation.builder().id("123")
                .userName("Santiago")
                .userId("123")
                .role(Role.SALA_ADMIN)
                .date(new Date(LocalDate.now(), LocalTime.now()))
                .roomId("room1")
                .state(State.RESERVA_CONFIRMADA)
                .people(5)
                .build();


        CustomUserDetails userDetails = new CustomUserDetails("123", "Santiago", "USUARIO", "token",Role.SALA_ADMIN.name(), "A");

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        lenient().when(authentication.getPrincipal()).thenReturn(userDetails);
        lenient().when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }


    @Test
    void shouldReturnReservationsByUserId() {
        when(reservationRepo.findReservationByUserId("u1")).thenReturn(List.of(reservation));
        List<Reservation> result = reservationService.getReservationsByUserId("u1");
        assertEquals(1, result.size());
        assertEquals("123", result.get(0).getId());
    }

    @Test
    void shouldThrowExceptionWhenUserHasNoReservations() {
        when(reservationRepo.findReservationByUserId("u2")).thenReturn(Collections.emptyList());
        assertThrows(ReservationNotFoundException.class, () -> reservationService.getReservationsByUserId("u2"));
    }

    @Test
    void shouldReturnReservationById() {
        when(reservationRepo.findById("123")).thenReturn(Optional.of(reservation));
        Reservation result = reservationService.getReservationById("123");
        assertEquals("room1", result.getRoomId());
    }

    @Test
    void shouldThrowExceptionWhenReservationNotFoundById() {
        when(reservationRepo.findById("999")).thenReturn(Optional.empty());
        assertThrows(ReservationNotFoundException.class, () -> reservationService.getReservationById("999"));
    }

    @Test
    void shouldReturnReservationsByDate() {
        Date date = reservation.getDate();
        when(reservationRepo.findReservationByDate(date)).thenReturn(List.of(reservation));
        List<Reservation> result = reservationService.getReservationsByDay(date);
        assertEquals(1, result.size());
    }

    @Test
    void shouldThrowExceptionWhenNoReservationsOnSpecificDay() {
        Date date = new Date(LocalDate.now(), LocalTime.of(15, 0));
        when(reservationRepo.findReservationByDate(date)).thenReturn(Collections.emptyList());
        assertThrows(ReservationNotFoundException.class, () -> reservationService.getReservationsByDay(date));
    }

    @Test
    void shouldReturnReservationsByRoomId() {
        when(reservationRepo.findReservationByRoomId("room1")).thenReturn(List.of(reservation));
        List<Reservation> result = reservationService.getReservationsByRoom("room1");
        assertFalse(result.isEmpty());
    }

    @Test
    void shouldThrowExceptionWhenNoReservationsInRoom() {
        when(reservationRepo.findReservationByRoomId("room2")).thenReturn(Collections.emptyList());
        assertThrows(ReservationNotFoundException.class, () -> reservationService.getReservationsByRoom("room2"));
    }

    @Test
    void shouldReturnReservationsByState() {
        when(reservationRepo.findReservationByState(State.RESERVA_CONFIRMADA)).thenReturn(List.of(reservation));
        List<Reservation> result = reservationService.getReservationsByState(State.RESERVA_CONFIRMADA);
        assertEquals(State.RESERVA_CONFIRMADA, result.get(0).getState());
    }

    @Test
    void shouldThrowExceptionWhenNoReservationsWithState() {
        when(reservationRepo.findReservationByState(State.RESERVA_CANCELADA)).thenReturn(Collections.emptyList());
        assertThrows(ReservationNotFoundException.class, () -> reservationService.getReservationsByState(State.RESERVA_CANCELADA));
    }
    @Test
    void shouldCreateReservationSuccessfully() {
        when(reservationRepo.findReservationByRoomIdAndDateAndUserId(anyString(), any(Date.class), anyString()))
                .thenReturn(Optional.empty());

        when(reservationRepo.save(any(Reservation.class))).thenReturn(reservation);

        Reservation result = reservationService.createReservation(reservation);

        verify(roomsService).reduceCapacityOfRoom("room1", 5);
        verify(reservationRepo).save(any(Reservation.class));
        verify(emailService).sendEmail(any(), any(), any());

        assertEquals("123", result.getUserId());
        assertEquals("token", result.getUserName());
    }
    @Test
    void shouldUpdateReservationSuccessfully() {
        String reservationId = "123";

        Reservation updated = Reservation.builder()
                .id(reservationId)
                .roomId("2")
                .userId("123")
                .userName("Santiago")
                .date(new Date(LocalDate.now().plusDays(1), LocalTime.now().plusHours(4)))
                .people(3)
                .state(State.RESERVA_CONFIRMADA)
                .build();

        when(reservationRepo.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(reservationRepo.save(any(Reservation.class))).thenReturn(updated);

        Reservation result = reservationService.updateReservation(reservationId, updated);

        assertEquals("2", result.getRoomId());
        assertEquals(3, result.getPeople());
        verify(reservationRepo).save(any(Reservation.class));
    }
    @Test
    void shouldDeleteReservationSuccessfully() {
        String reservationId = "123";
        reservation.setState(State.RESERVA_CONFIRMADA);

        when(reservationRepo.findById(reservationId)).thenReturn(Optional.of(reservation));

        reservationService.deleteReservation(reservationId);

        assertEquals(State.RESERVA_CONFIRMADA, reservation.getState());
        verify(reservationRepo).findById(reservationId);
        verify(reservationRepo).deleteById(reservationId);
        verify(reservationRepo, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenReservationToUpdateNotFound() {
        when(reservationRepo.findById("not-found")).thenReturn(Optional.empty());

        assertThrows(ReservationNotFoundException.class, () ->
                reservationService.updateReservation("not-found", reservation));
    }

    @Test
    void shouldThrowExceptionWhenReservationToDeleteNotFound() {
        when(reservationRepo.findById("missing")).thenReturn(Optional.empty());

        assertThrows(ReservationNotFoundException.class, () ->
                reservationService.deleteReservation("missing"));
    }
    @Test
    void shouldChangeReservationStateSuccessfully() {
        String reservationId = "123";
        State newState = State.RESERVA_CONFIRMADA;

        when(reservationRepo.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(reservationRepo.save(any(Reservation.class))).thenReturn(reservation);

        reservationService.changeReservationState(reservationId, newState);

        assertEquals(newState, reservation.getState());
        verify(reservationRepo).save(reservation);
        verify(roomsService, never()).increaseCapacityOfRoom(anyString(), anyInt());
    }

    @Test
    void shouldIncreaseRoomCapacityWhenStateIsCancelled() {
        String reservationId = "123";
        State newState = State.RESERVA_CANCELADA;

        when(reservationRepo.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(reservationRepo.save(any(Reservation.class))).thenReturn(reservation);

        reservationService.changeReservationState(reservationId, newState);

        assertEquals(newState, reservation.getState());
        verify(roomsService).increaseCapacityOfRoom(reservation.getRoomId(), reservation.getPeople());
        verify(reservationRepo).save(reservation);
    }

    @Test
    void shouldIncreaseRoomCapacityWhenStateIsTerminated() {
        String reservationId = "123";
        State newState = State.RESERVA_TERMINADA;

        when(reservationRepo.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(reservationRepo.save(any(Reservation.class))).thenReturn(reservation);

        reservationService.changeReservationState(reservationId, newState);

        assertEquals(newState, reservation.getState());
        verify(roomsService).increaseCapacityOfRoom(reservation.getRoomId(), reservation.getPeople());
        verify(reservationRepo).save(reservation);
    }

    @Test
    void shouldThrowExceptionWhenStateIsInvalid() {
        String reservationId = "123";
        State invalidState = null;

        when(reservationRepo.findById(reservationId)).thenReturn(Optional.of(reservation));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> {
            reservationService.changeReservationState(reservationId, invalidState);
        });

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        verify(reservationRepo, never()).save(any());
        verify(roomsService, never()).increaseCapacityOfRoom(anyString(), anyInt());
    }

    @Test
    void shouldThrowExceptionWhenReservationNotFound() {
        when(reservationRepo.findById("not-found")).thenReturn(Optional.empty());

        assertThrows(ReservationNotFoundException.class, () -> {
            reservationService.changeReservationState("not-found", State.RESERVA_CONFIRMADA);
        });

        verify(reservationRepo, never()).save(any());
        verify(roomsService, never()).increaseCapacityOfRoom(anyString(), anyInt());
    }



}

