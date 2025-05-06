package eci.cvds.mod2.exceptions;

public class ReservationException {
    public static final String REV_NOT_FOUND = "Reservation not found";
    public static final String REV_ALREADY_EXIST = "That reservation has been booked under that id, in the same room at the same time";
    public static final String USER_HAS_NO_RESERVATIONS = "There are no reservations under that user id";
    public static final String NO_RESERVATIONS_ON_SPECIFIC_DAY = "There are no reservations the requested";
    public static final String NO_RESERVATIONS_IN_SPECIFIC_ROOM = "There are no reservations in that room";
    public static final String NO_RESERVATION_WITH_THAT_STATE = "There are no reservations with that state";
    public static final String NOT_VALID_DATE_OF_RESERVATION= "The given date is not valid, check the day or hour remember you can only book a reservation at least 2 hours before the hour of the reservation on the same day ";
}
