package eci.cvds.mod2.services;

import eci.cvds.mod2.modules.Reservation;
import eci.cvds.mod2.modules.Room;
import eci.cvds.mod2.reposistories.ReservationRepo;
import eci.cvds.mod2.reposistories.RoomRepo;
import eci.cvds.mod2.util.Date;
import eci.cvds.mod2.util.Role;
import eci.cvds.mod2.util.State;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @Mock
    private RoomRepo roomRepo;

    @Mock
    private ReservationRepo reservationRepo;

    @InjectMocks
    private ReservationService reservationService;

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void testCreateReservationCallsReservationRepo() {
        Room room1 = new Room("R1", "Building A", "Main Building", 10, 5);
        Reservation reservation = new Reservation(
                "1", "John Doe", "123456", Role.STUDENT, new Date(), "R1", "Building A",
                new HashSet<>(Collections.singletonList("Loan1")), new HashSet<>(Collections.singletonList("Game1")),
                State.RESERVA_CONFIRMADA, 4, false, room1
        );

        reservationService.createReservation(reservation);

        Mockito.verify(reservationRepo, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void testGetAvailableRoomsForPeopleCount() {
        Room room1 = new Room("R1", "Building A", "Main Building", 10, 5);
        Room room2 = new Room("R2", "Building B", "Secondary Building", 8, 8);
        Room room3 = new Room("R3", "Building C", "North Wing", 12, 9);

        Mockito.when(roomRepo.findAll()).thenReturn(List.of(room1, room2, room3));

        int requiredPeople = 4;
        List<Room> availableRooms = reservationService.getAvailableRooms(requiredPeople);

        Assertions.assertEquals(1, availableRooms.size());
        Assertions.assertEquals("R1", availableRooms.get(0).getRoomId());
    }

    @Test
    public void testUpdateReservation() {
        Room room = new Room("R1", "Building A", "Main Building", 10, 5);
        Reservation existingReservation = new Reservation("1", "John Doe", "123456", Role.STUDENT, new Date(), "R1", "Building A",
                new HashSet<>(Collections.singletonList("Loan1")), new HashSet<>(Collections.singletonList("Game1")),
                State.RESERVA_CONFIRMADA, 4, false, room);

        Reservation updatedReservation = new Reservation("1", "John Doe", "123456", Role.STUDENT, new Date(), "R1", "Building A",
                new HashSet<>(Collections.singletonList("Loan1")), new HashSet<>(Collections.singletonList("Game1")),
                State.RESERVA_CANCELADA, 4, true, room);

        Mockito.when(reservationRepo.findById("1")).thenReturn(Optional.of(existingReservation));
        Mockito.when(reservationRepo.save(updatedReservation)).thenReturn(updatedReservation);

        Reservation result = reservationService.updateReservation("1", updatedReservation);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(State.RESERVA_CANCELADA, result.getState());
    }

    @Test
    public void testDeleteReservation() {
        Room room = new Room("R1", "Building A", "Main Building", 10, 5);
        Reservation reservationToDelete = new Reservation("1", "John Doe", "123456", Role.STUDENT, new Date(), "R1", "Building A",
                new HashSet<>(Collections.singletonList("Loan1")), new HashSet<>(Collections.singletonList("Game1")),
                State.RESERVA_CONFIRMADA, 4, false, room);

        Mockito.when(reservationRepo.findById("1")).thenReturn(Optional.of(reservationToDelete));

        Reservation deletedReservation = reservationService.deleteReservation("1");

        Assertions.assertNotNull(deletedReservation);
        Assertions.assertEquals("1", deletedReservation.getId());
    }

    @Test
    public void testGetReservationsByRole() {
        Room room1 = new Room("R1", "Building A", "Main Building", 10, 5);
        Reservation reservation1 = new Reservation("1", "John Doe", "123456", Role.STUDENT, new Date(), "R1", "Building A",
                new HashSet<>(Collections.singletonList("Loan1")), new HashSet<>(Collections.singletonList("Game1")),
                State.RESERVA_CONFIRMADA, 4, false, room1);

        Reservation reservation2 = new Reservation("2", "Jane Doe", "654321", Role.TEACHER, new Date(), "R2", "Building B",
                new HashSet<>(Collections.singletonList("Loan2")), new HashSet<>(Collections.singletonList("Game2")),
                State.RESERVA_CANCELADA, 2, true, room1);

        Mockito.when(reservationRepo.findByRole(Role.STUDENT)).thenReturn(List.of(reservation1));

        List<Reservation> reservations = reservationService.getReservationsByUserRole(Role.STUDENT);

        Assertions.assertEquals(1, reservations.size());
        Assertions.assertEquals("John Doe", reservations.get(0).getUserName());
    }

    @Test
    public void testGetReservationsByState() {
        Room room1 = new Room("R1", "Building A", "Main Building", 10, 5);
        Reservation confirmedReservation = new Reservation("1", "John Doe", "123456", Role.STUDENT, new Date(), "R1", "Building A",
                new HashSet<>(Collections.singletonList("Loan1")), new HashSet<>(Collections.singletonList("Game1")),
                State.RESERVA_CONFIRMADA, 4, false, room1);

        Reservation canceledReservation = new Reservation("2", "Jane Doe", "654321", Role.TEACHER, new Date(), "R2", "Building B",
                new HashSet<>(Collections.singletonList("Loan2")), new HashSet<>(Collections.singletonList("Game2")),
                State.RESERVA_CANCELADA, 2, true, room1);

        Mockito.when(reservationRepo.findByState(State.RESERVA_CONFIRMADA)).thenReturn(List.of(confirmedReservation));
        Mockito.when(reservationRepo.findByState(State.RESERVA_CANCELADA)).thenReturn(List.of(canceledReservation));

        List<Reservation> confirmedReservations = reservationService.getReservationsByState(true);
        List<Reservation> canceledReservations = reservationService.getReservationsByState(false);

        Assertions.assertEquals(1, confirmedReservations.size());
        Assertions.assertEquals(1, canceledReservations.size());
    }
}
