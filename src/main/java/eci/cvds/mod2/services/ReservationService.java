package eci.cvds.mod2.services;

import eci.cvds.mod2.controllers.LoanController;
import eci.cvds.mod2.controllers.RoomController;
import eci.cvds.mod2.exceptions.*;
import eci.cvds.mod2.modules.Loan;
import eci.cvds.mod2.modules.Reservation;
import eci.cvds.mod2.modules.Room;
import eci.cvds.mod2.reposistories.ReservationRepo;
import eci.cvds.mod2.util.Date;
import eci.cvds.mod2.util.Role;
import eci.cvds.mod2.util.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Stack;

@Service
public class ReservationService {
    private final ReservationRepo reservationRepo;
    private final RoomController roomController;
    private final LoanController loanController;
    private final IEmailService emailService;
    @Autowired
    public ReservationService(ReservationRepo reservationRepo, RoomController roomController, LoanController loanController, IEmailService emailService){
        this.reservationRepo = reservationRepo;
        this.roomController = roomController;
        this.loanController=loanController;
        this.emailService=emailService;
    }

    public List<Reservation> getReservationsByUserId(String userId) {
        List<Reservation> reservations = reservationRepo.findReservationByUserId(userId);
        if (reservations.isEmpty()) {
            throw new ReservationNotFoundException(ReservationException.USER_HAS_NO_RESERVATIONS);
        }
        return reservations;
    }


    public List<Reservation> getReservationsByUserRole(Role role) {
        return null;
    }

    public Reservation getReservationById(String revId) {
        return reservationRepo.findById(revId).
                orElseThrow(()-> new ReservationNotFoundException(ReservationException.REV_NOT_FOUND));
    }

    public List<Reservation> getReservationsByDay(Date date) {
        List<Reservation> reservations = reservationRepo.findReservationByDate(date);
        if (reservations.isEmpty()) {
                throw new ReservationNotFoundException(ReservationException.NO_RESERVATIONS_ON_SPECIFIC_DAY);
        }
        return reservations;
    }

    public List<Reservation> getReservationsByRoom(String roomId) {
        List<Reservation> reservations = reservationRepo.findReservationByRoomId(roomId);
        if (reservations.isEmpty()) {
            throw new ReservationNotFoundException(ReservationException.NO_RESERVATIONS_IN_SPECIFIC_ROOM);
        }
        return reservations;

    }

    public List<Reservation> getReservationsByState(State state) {
        List<Reservation> reservations = reservationRepo.findReservationByState(state);
        if (reservations.isEmpty()) {
            throw new ReservationNotFoundException(ReservationException.NO_RESERVATION_WITH_THAT_STATE);
        }

        return reservations;

    }

    public Reservation createReservation(Reservation rev) {
//        if (!Date.checkValidDate(rev.getDate())) {
//            throw new ReservationNotFoundException(ReservationException.NOT_VALID_DATE_OF_RESERVATION);
//        }
        if (reservationRepo.findReservationByRoomIdAndDateAndUserId(rev.getRoomId(), rev.getDate(), rev.getUserId()).isPresent()) {
            throw new ReservationNotFoundException(ReservationException.REV_ALREADY_EXIST);
        }

        roomController.reduceCapacityOfRoom(rev.getRoomId(), rev.getPeople());
        Reservation savedReservation = reservationRepo.save(rev);

        String[] to = {"santiago.amador-d@mail.escuelaing.edu.co"}; // Asegúrate de tener el correo real
        String subject = "Confirmación de reserva";
        String message = "Hola " + rev.getUserName() + ",\n\nTu reserva ha sido creada exitosamente para el día " +
                rev.getDate().getDay().toString() + " a las " + rev.getDate().getTime().toString() +
                " en la sala " + rev.getRoomId() + ".\n\nGracias.";

        emailService.sendEmail(to, subject, message);

        return savedReservation;
    }

    public Reservation updateReservation(String revId, Reservation newRev) {
        Reservation reservation = reservationRepo.findById(revId)
                .orElseThrow(()-> new ReservationNotFoundException(ReservationException.REV_NOT_FOUND));
        roomController.getRoomById(newRev.getRoomId());
        reservation.setUserName(newRev.getUserName());
        reservation.setUserId(newRev.getUserId());
        reservation.setRole(newRev.getRole());
        reservation.setDate(newRev.getDate());
        reservation.setRoomId(newRev.getRoomId());
        reservation.setPeople(newRev.getPeople());
        reservation.setLoans(newRev.getLoans());
        return reservationRepo.save(reservation);

    }

    public Reservation deleteReservation(String revId) {
        Reservation reservation = reservationRepo.findById(revId)
                .orElseThrow(()-> new RoomNotFoundException(RoomException.ROOM_NOT_FOUND));
        reservationRepo.deleteById(revId);
        return reservation;
    }
    public void changeReservationState(@PathVariable String revId, @PathVariable State state){
        Reservation reservation = reservationRepo.findById(revId)
                .orElseThrow(()-> new ReservationNotFoundException(ReservationException.REV_NOT_FOUND));
        if(!State.isValidState(state)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid state value");

        }
        reservation.setState(state);
        if(state.equals(State.RESERVA_CANCELADA)|| state.equals(State.RESERVA_TERMINADA)){
            roomController.increaseCapacityOfRoom(reservation.getRoomId(), reservation.getPeople());
        }
        reservationRepo.save(reservation);
    }
    public List<Reservation> getAll() {
        return reservationRepo.findAll();
    }
    public Reservation addLoan(String revId,String loanId){
        Reservation reservation= this.getReservationById(revId);
        loanController.getLoanById(loanId);
        reservation.addLoan(loanId);
        return reservationRepo.save(reservation);
    }
}
