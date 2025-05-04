package eci.cvds.mod2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import eci.cvds.mod2.controllers.ReservationController;
import eci.cvds.mod2.modules.Reservation;
import eci.cvds.mod2.services.ReservationService;
import eci.cvds.mod2.util.Role;
import eci.cvds.mod2.util.State;

@SpringBootTest
public class ReservationTest {

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ReservationController reservationController;

    @Test
    void testGetReservationsByUserId() {
        String userId = "user123";
        Set<String> loans = new HashSet<>();
        Set<String> elements = new HashSet<>();
        Reservation reservation = new Reservation(userId,"nicolas","456",Role.STUDENT, null,"789",loans, elements,State.RESERVA_CONFIRMADA, 30);
        List<Reservation> reservations = List.of(reservation);
        when(reservationService.getReservationsByUserId(userId)).thenReturn(reservations);
        List<Reservation> result = reservationController.getReservationsByUserId(userId);
        assertEquals(1, result.size());
        assertEquals(userId, result.get(0).getUserId());
    }

    @Test
    void testGetReservationsByUserRole() {
        Role role = Role.STUDENT;
        Set<String> loans = new HashSet<>();
        Set<String> elements = new HashSet<>();
        Reservation reservation = new Reservation("123","nicolas","456",role, null,"789",loans, elements,State.RESERVA_CONFIRMADA, 30);
        List<Reservation> reservations = List.of(reservation);
        when(reservationService.getReservationsByUserRole(role)).thenReturn(reservations);
        List<Reservation> result = reservationController.getReservationsByUserRole(role);
        assertEquals(1, result.size());
        assertEquals(role, result.get(0).getRole());
    }

    @Test
    void testGetReservationById_Found() {
        String id = "res123";
        Set<String> loans = new HashSet<>();
        Set<String> elements = new HashSet<>();
        Reservation reservation = new Reservation(id,"nicolas","456",Role.STUDENT, null,"789",loans, elements,State.RESERVA_CONFIRMADA, 30);
        when(reservationService.getReservationById(id).get()).thenReturn((reservation));
        ResponseEntity<Reservation> response = reservationController.getReservationById(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reservation, response.getBody());
    }

    @Test
    void testGetReservationById_NotFound() {
        String id = "res999";
        when(reservationService.getReservationById(id)).thenReturn(null);
        ResponseEntity<Reservation> response = reservationController.getReservationById(id);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    // verificar como manejaran el date antes de probar
    // @Test
    // void testGetReservationsByDay() {
    //     Date date = new Date();
    //     Set<String> loans = new HashSet<>();
    //     Set<String> elements = new HashSet<>();
    //     Date date2 = new Date();
    //     Reservation reservation = new Reservation("123","nicolas","456",Role.STUDENT,null,"789",loans, elements,State.RESERVA_CONFIRMADA, 30);
    //     reservation.setDate(null);
    //     List<Reservation> reservations = List.of(reservation);
    //     when(reservationService.getReservationsByDay(date)).thenReturn(reservations);

    //     List<Reservation> result = reservationController.getReservationsByDay(date);

    //     assertEquals(1, result.size());
    //     assertEquals(date, result.get(0).getDate());
    // }

    @Test
    void testGetReservationsByRoom() {
        String room = "R101";
        Set<String> loans = new HashSet<>();
        Set<String> elements = new HashSet<>();
        Reservation reservation = new Reservation("123","nicolas","456",Role.STUDENT, null,room,loans, elements,State.RESERVA_CONFIRMADA, 30);
        reservation.setRoomId(room);
        List<Reservation> reservations = List.of(reservation);
        when(reservationService.getReservationsByRoom(room)).thenReturn(reservations);

        List<Reservation> result = reservationController.getReservationsByRoom(room);

        assertEquals(1, result.size());
        assertEquals(room, result.get(0).getRoomId());
    }

    @Test
    void testGetReservationsByState() {
        State state = State.RESERVA_CONFIRMADA;
        Set<String> loans = new HashSet<>();
        Set<String> elements = new HashSet<>();
        Reservation reservation = new Reservation("123","nicolas","456",Role.STUDENT, null,"789",loans, elements,state, 30);
        reservation.setState(state);
        List<Reservation> reservations = List.of(reservation);
        when(reservationService.getReservationsByState(state)).thenReturn(reservations);

        List<Reservation> result = reservationController.getReservationsByState(state);

        assertEquals(1, result.size());
        assertEquals(state, result.get(0).getState());
    }

    @Test
    void testCreateReservation_Success() {
        Set<String> loans = new HashSet<>();
        Set<String> elements = new HashSet<>();
        Reservation reservation = new Reservation("123","nicolas","456",Role.STUDENT, null,"789",loans, elements,State.RESERVA_CONFIRMADA, 30);
        when(reservationService.createReservation(reservation)).thenReturn(reservation);

        ResponseEntity<Reservation> response = reservationController.createReservation(reservation);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(reservation, response.getBody());
    }

    @Test
    void testCreateReservation_Failure() {
        Set<String> loans = new HashSet<>();
        Set<String> elements = new HashSet<>();
        Reservation reservation = new Reservation("123","nicolas","456",Role.STUDENT, null,"789",loans, elements,State.RESERVA_CONFIRMADA, 30);
        when(reservationService.createReservation(reservation)).thenReturn(null);

        ResponseEntity<Reservation> response = reservationController.createReservation(reservation);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testUpdateReservation_Success() {
        String user = "cedab23";
        Set<String> loans = new HashSet<>();
        Set<String> elements = new HashSet<>();
        Reservation reservation = new Reservation("123","nicolas","456",Role.STUDENT, null,"789",loans, elements,State.RESERVA_CONFIRMADA, 30);
        reservation.setUserName(user);
        when(reservationService.updateReservation(user, reservation)).thenReturn(reservation);

        ResponseEntity<Reservation> response = reservationController.updateReservation(user, reservation);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reservation, response.getBody());
    }

    @Test
    void testUpdateReservation_Failure() {
        String user = "cedab23";
        Set<String> loans = new HashSet<>();
        Set<String> elements = new HashSet<>();
        Reservation reservation = new Reservation("123","nicolas","456",Role.STUDENT, null,"789",loans, elements,State.RESERVA_CONFIRMADA, 30);
        reservation.setUserName(user);
        when(reservationService.updateReservation(user, reservation)).thenReturn(null);

        ResponseEntity<Reservation> response = reservationController.updateReservation(user, reservation);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testDeleteReservation_Success() {
        String id = "res123";
        when(reservationService.deleteReservation(id)).thenReturn(true);

        ResponseEntity<String> response = reservationController.deleteReservation(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Reservation successfully deleted", response.getBody());
    }

    @Test
    void testDeleteReservation_Failure() {
        String id = "res123";
        when(reservationService.deleteReservation(id)).thenReturn(false);

        ResponseEntity<String> response = reservationController.deleteReservation(id);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetAllReservations() {
        Set<String> loans = new HashSet<>();
        Set<String> elements = new HashSet<>();
        Reservation reservation1 = new Reservation("123","nicolas","456",Role.STUDENT, null,"789",loans, elements,State.RESERVA_CONFIRMADA, 30);

        Set<String> loans2 = new HashSet<>();
        Set<String> elements2 = new HashSet<>();
        Reservation reservation2 = new Reservation("789","cedab23","654",Role.ADMIN, null,"123",loans2, elements2,State.RESERVA_CANCELADA, 40);

        List<Reservation> list = List.of(reservation1, reservation2);
        when(reservationService.getAll()).thenReturn(list);

        List<Reservation> result = reservationController.getAll();

        assertEquals(2, result.size());
        assertEquals(list, result);
    }
}
    
