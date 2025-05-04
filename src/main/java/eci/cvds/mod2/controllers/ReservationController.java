package eci.cvds.mod2.controllers;

import eci.cvds.mod2.modules.Reservation;
import eci.cvds.mod2.services.ReservationService;
import eci.cvds.mod2.util.Date;
import eci.cvds.mod2.util.Role;
import eci.cvds.mod2.util.State;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/revs")
@CrossOrigin(origins = "*")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/user/{userId}")
    public List<Reservation> getReservationsByUserId(@PathVariable String userId) {
        return reservationService.getReservationsByUserId(userId);
    }

    @GetMapping("/role/{role}")
    public List<Reservation> getReservationsByUserRole(@PathVariable Role role) {
        return reservationService.getReservationsByUserRole(role);
    }

    @GetMapping("/id/{revId}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable String revId) {
        Optional<Reservation> reservation = reservationService.getReservationById(revId);
        if (reservation.isPresent()) {
            return ResponseEntity.ok(reservation.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/date")
    public List<Reservation> getReservationsByDay(@RequestBody Date date) {
        return reservationService.getReservationsByDay(date);
    }

    @GetMapping("/room/{roomId}")
    public List<Reservation> getReservationsByRoom(@PathVariable String roomId) {
        return reservationService.getReservationsByRoom(roomId);
    }

    @GetMapping("/state/{state}")
    public List<Reservation> getReservationsByState(@PathVariable State state) {
        return reservationService.getReservationsByState(state);
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation rev) {
        Reservation newReservation = reservationService.createReservation(rev);
        if (newReservation != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(newReservation);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{revId}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable String revId, @RequestBody Reservation newRev) {
        Reservation updatedReservation = reservationService.updateReservation(revId, newRev);
        if (updatedReservation != null) {
            return ResponseEntity.ok(updatedReservation);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    @DeleteMapping("/{revId}")
    public ResponseEntity<String> deleteReservation(@PathVariable String revId) {
        boolean deleted = reservationService.deleteReservation(revId);
        if (deleted) {
            return ResponseEntity.ok().body("Reservation successfully deleted");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping
    public List<Reservation> getAll() {
        return reservationService.getAll();
    }
}
