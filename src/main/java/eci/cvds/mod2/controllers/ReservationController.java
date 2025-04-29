package eci.cvds.mod2.controllers;

import eci.cvds.mod2.modules.Reservation;
import eci.cvds.mod2.services.ReservationService;
import eci.cvds.mod2.util.Date;
import eci.cvds.mod2.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/revs")
@CrossOrigin(origins = "*")
public class ReservationController {
    ReservationService reservationService;
    @Autowired
    public ReservationController(ReservationService reservationService){
        this.reservationService = reservationService;
    }
    public List<Reservation> getReservationsByUserId(String userId) {
        return null;
    }

    public List<Reservation> getReservationsByUserRole(Role role) {
        return null;
    }

    public ResponseEntity<Reservation> getReservationById(String revId) {
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

    public ResponseEntity<Reservation> createReservation(Reservation rev) {
        return null;
    }

    public ResponseEntity<Reservation> updateReservation(Reservation rev, Reservation newRev) {
        return null;
    }

    public ResponseEntity<Reservation> deleteReservation(Reservation rev) {
        return null;
    }

    public List<Reservation> getAll() {
        return null;
    }
}
