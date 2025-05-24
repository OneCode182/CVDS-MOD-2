package eci.cvds.mod2.services;

import eci.cvds.mod2.controllers.LoanController;
import eci.cvds.mod2.controllers.RoomController;
import eci.cvds.mod2.exceptions.*;
import eci.cvds.mod2.modules.CustomUserDetails;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final IEmailService emailService;
    private CustomUserDetails getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (CustomUserDetails) auth.getPrincipal();
    }


    @Autowired
    public ReservationService(ReservationRepo reservationRepo, RoomController roomController, IEmailService emailService){
        this.reservationRepo = reservationRepo;
        this.roomController = roomController;
        this.emailService=emailService;
    }
    private void checkAdminOrAdminstrativoRole() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
        Role role = Role.fromString(user.getRole());
        if (!(role.equals(Role.SALA_ADMIN))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permisos para realizar esta acción");
        }
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
        CustomUserDetails user = getCurrentUser();
        rev.setUserId(user.getId());
        rev.setUserName(user.getName());
        rev.setRole(Role.fromString(user.getRole()));
        if (reservationRepo.findReservationByRoomIdAndDateAndUserId(rev.getRoomId(), rev.getDate(), rev.getUserId()).isPresent()) {
            throw new ReservationNotFoundException(ReservationException.REV_ALREADY_EXIST);
        }
        roomController.reduceCapacityOfRoom(rev.getRoomId(), rev.getPeople());
        Reservation savedReservation = reservationRepo.save(rev);

        String[] to = {"santiago.amador-d@mail.escuelaing.edu.co"};
        String subject = "Confirmación de reserva";
        String message = "Hola " + rev.getUserName() + ",\n\nTu reserva ha sido creada exitosamente para el día " +
                rev.getDate().getDay().toString() + " a las " + rev.getDate().getTime().toString() +
                " en la sala " + rev.getRoomId() + ".\n\nGracias.";

        try{
            emailService.sendEmail(to, subject, message);
        }catch(Exception e){
            System.err.println("Error al enviar el correo" + e.getMessage());
        }

        return savedReservation;
    }

    public Reservation updateReservation(String revId, Reservation newRev) {
        Reservation reservation = reservationRepo.findById(revId)
                .orElseThrow(()-> new ReservationNotFoundException(ReservationException.REV_NOT_FOUND));
        roomController.getRoomById(newRev.getRoomId());

        reservation.setDate(newRev.getDate());
        reservation.setRoomId(newRev.getRoomId());
        reservation.setPeople(newRev.getPeople());

        return reservationRepo.save(reservation);

    }

    public Reservation deleteReservation(String revId) {
        Reservation reservation = reservationRepo.findById(revId)
                .orElseThrow(()-> new RoomNotFoundException(RoomException.ROOM_NOT_FOUND));
        reservationRepo.deleteById(revId);
        roomController.increaseCapacityOfRoom(reservation.getRoomId(), reservation.getPeople());
        return reservation;
    }
    public void changeReservationState(String revId, State state){
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

}
