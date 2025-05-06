package eci.cvds.mod2.services;

import eci.cvds.mod2.modules.Reservation;
import eci.cvds.mod2.modules.Room;
import eci.cvds.mod2.reposistories.ReservationRepo;
import eci.cvds.mod2.reposistories.RoomRepo;
import eci.cvds.mod2.util.Date;
import eci.cvds.mod2.util.Role;
import eci.cvds.mod2.util.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    private final ReservationRepo reservationRepo;
    private final RoomRepo roomRepo;

    @Autowired
    public ReservationService(ReservationRepo reservationRepo, RoomRepo roomRepo) {
        this.reservationRepo = reservationRepo;
        this.roomRepo = roomRepo;
    }

    public List<Reservation> getReservationsByUserId(String userId) {
        return reservationRepo.findByUserId(userId);
    }

    public List<Reservation> getReservationsByUserRole(Role role) {
        return reservationRepo.findByRole(role);
    }

    public Reservation getReservationById(String revId) {
        return reservationRepo.findById(revId).orElse(null);
    }

    public List<Reservation> getReservationsByDay(Date date) {
        return reservationRepo.findByDate(date);
    }

    public List<Reservation> getReservationsByRoom(String roomId) {
        return reservationRepo.findByRoomId(roomId);
    }

    public List<Reservation> getReservationsByState(boolean stateBool) {
        State state = stateBool ? State.RESERVA_CONFIRMADA : State.RESERVA_CANCELADA;
        return reservationRepo.findByState(state);
    }

    public Reservation createReservation(Reservation rev) {

        Reservation createdReservation = reservationRepo.save(rev);
        List<Reservation> activeReservations = reservationRepo.findByRoomId(rev.getRoomId())
                .stream()
                .filter(r -> r.getState() == State.RESERVA_CONFIRMADA)
                .toList();

        int totalPeople = activeReservations.stream().mapToInt(Reservation::getPeople).sum();

        Optional<Room> optionalRoom = roomRepo.findById(rev.getRoomId());
        if (optionalRoom.isPresent()) {
            Room room = optionalRoom.get();
            room.setCurrentPeople(totalPeople);
            roomRepo.save(room);
        }
        return createdReservation;
    }
  
  public Reservation updateReservation(String rev, Reservation newRev) {
        if (!reservationRepo.existsById(rev)) {
            return null;
        }
        Reservation updatedrReservation = reservationRepo.findById(rev).orElse(null);
        if (updatedrReservation == null) {
            return null;
        }
        updatedrReservation.setDate(newRev.getDate());
        updatedrReservation.setPeople(newRev.getPeople());
        updatedrReservation.setRole(newRev.getRole());
        updatedrReservation.setRoomId(newRev.getRoomId());
        updatedrReservation.setState(newRev.getState());
        updatedrReservation.setUserId(newRev.getUserId());
        updatedrReservation.setUserName(newRev.getUserName());
        return reservationRepo.save(newRev);
    }
  
    public Reservation deleteReservation(String revId) {
        Optional<Reservation> optional = reservationRepo.findById(revId);
        if (optional.isPresent()) {
            reservationRepo.deleteById(revId);
            return optional.get();
        }
        return null;

    }

    public List<Room> getAvailableRooms(int requiredPeople) {
        return roomRepo.findAll().stream()
                .filter(room -> room.getCapacity() - room.getCurrentPeople() >= requiredPeople)
                .toList();
    }


    public List<Reservation> getAll() {
        return reservationRepo.findAll();
    }
}
