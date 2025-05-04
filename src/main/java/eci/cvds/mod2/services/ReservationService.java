package eci.cvds.mod2.services;

import eci.cvds.mod2.modules.Reservation;
import eci.cvds.mod2.reposistories.ReservationRepo;
import eci.cvds.mod2.util.Date;
import eci.cvds.mod2.util.Role;
import eci.cvds.mod2.util.State;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    private ReservationRepo reservationRepo;

    @Autowired
    public ReservationService(ReservationRepo reservationRepo) {
        this.reservationRepo = reservationRepo;
    }

    public List<Reservation> getReservationsByUserId(String userId) {
        return reservationRepo.findByUserId(userId);
    }

    public List<Reservation> getReservationsByUserRole(Role role) {
        return reservationRepo.findByRole(role);
    }

    public Optional<Reservation> getReservationById(String revId) {
        return reservationRepo.findById(revId);
    }

    public List<Reservation> getReservationsByDay(Date date) {
        return reservationRepo.findByDates(date);
    }

    public List<Reservation> getReservationsByRoom(String roomId) {
        return reservationRepo.findByRoom(roomId);
    }

    public List<Reservation> getReservationsByState(State state) {
        return reservationRepo.findByState(state);
    }

    public Reservation createReservation(Reservation rev) {
        try {
            Reservation savedReservation = reservationRepo.save(rev);
            return savedReservation;
        } catch (Exception e) {
            return null;
        }
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

    public boolean deleteReservation(String revId) {
        try {
            reservationRepo.deleteById(revId);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public List<Reservation> getAll() {
        return reservationRepo.findAll();
    }
}
