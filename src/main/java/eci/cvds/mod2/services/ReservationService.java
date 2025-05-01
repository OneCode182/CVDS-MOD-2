package eci.cvds.mod2.services;

import eci.cvds.mod2.modules.Reservation;
import eci.cvds.mod2.reposistories.ReservationRepo;
import eci.cvds.mod2.util.Date;
import eci.cvds.mod2.exceptions.ReservationException;
import eci.cvds.mod2.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    private final ReservationRepo reservationRepo;
    @Autowired
    public ReservationService(ReservationRepo reservationRepo){
        this.reservationRepo = reservationRepo;
    }

    public List<Reservation> getReservationsByUserId(String userId) {
        return reservationRepo.getReservationByUserId(userId);
    }

    public List<Reservation> getReservationsByUserRole(Role role) {

        return null;
    }

    public Reservation getReservationById(String revId) {
        Optional<Reservation> optionalReservation = reservationRepo.findById(revId);
        if(optionalReservation.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ReservationException.REV_NOT_FOUND);
        }
        return optionalReservation.get();
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

    public Reservation updateReservation(String rev, Reservation newRev) {
        return null;
    }

    public Reservation deleteReservation(String revId) {
        return null;
    }

    public List<Reservation> getAll() {
        return null;
    }
}
