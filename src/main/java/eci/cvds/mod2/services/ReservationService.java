package eci.cvds.mod2.services;

import eci.cvds.mod2.modules.Reservation;
import eci.cvds.mod2.reposistories.ReservationRepo;
import eci.cvds.mod2.util.Date;
import eci.cvds.mod2.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    private ReservationRepo reservationRepo;
    @Autowired
    public ReservationService(ReservationRepo reservationRepo){
        this.reservationRepo = reservationRepo;
    }

    public List<Reservation> getReservationsByUserId(String userId) {
        return null;
    }

    public List<Reservation> getReservationsByUserRole(Role role) {
        return null;
    }

    public Reservation getReservationById(String revId) {
        return null;
    }

    public List<Reservation> getReservationsByDay(Date date) {
        return null;
    }

    public List<Reservation> getReservationsByRoom(String roomId) {
        return null;
    }

    public List<Reservation> getReservationsByState(boolean state) {
        return null;
    }

    public Reservation createReservation(Reservation rev) {
        return null;
    }

    public Reservation updateReservation(Reservation rev, Reservation newRev) {
        return null;
    }

    public Reservation deleteReservation(Reservation rev) {
        return null;
    }

    public List<Reservation> getAll() {
        return null;
    }
}
