package eci.cvds.mod2.controllers;

import eci.cvds.mod2.modules.Reservation;
import eci.cvds.mod2.services.ReservationService;
import eci.cvds.mod2.util.Date;
import eci.cvds.mod2.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<Reservation>> getReservationsByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(reservationService.getReservationsByUserId(userId));
    }

    @GetMapping("/role/{role}")
    public List<Reservation> getReservationsByUserRole(@PathVariable Role role) {
        return reservationService.getReservationsByUserRole(role);
    }

    @GetMapping("/id/{revId}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable String revId) {
        return ResponseEntity.ok(reservationService.getReservationById(revId));
    }

    @GetMapping("/date")
    public ResponseEntity<List<Reservation>> getReservationsByDay(@RequestBody Date date) {
        return ResponseEntity.ok(reservationService.getReservationsByDay(date));
    }

    @GetMapping("/room/{roomId}")
    public List<Reservation> getReservationsByRoom(@PathVariable String roomId) {
        return reservationService.getReservationsByRoom(roomId);
    }

    @GetMapping("/state/{state}")
    public List<Reservation> getReservationsByState(@PathVariable boolean state) {
        return reservationService.getReservationsByState(state);
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation rev) {
        Reservation created = reservationService.createReservation(rev);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{revId}")
    public ResponseEntity<String> updateReservation(@PathVariable String revId, @RequestBody Reservation newRev) {
        reservationService.updateReservation(revId, newRev);
        return ResponseEntity.ok("Reservation successfully updated");
    }

    @DeleteMapping("/{revId}")
    public ResponseEntity<String> deleteReservation(@PathVariable String revId) {
        reservationService.deleteReservation(revId);
        return ResponseEntity.ok("Reservation successfully deleted");
    }
    @GetMapping
    public List<Reservation> getAll() {
        return reservationService.getAll();
    }
}
