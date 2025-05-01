package eci.cvds.mod2.exceptions;

public class RoomAlreadyExistException extends RuntimeException{
    public RoomAlreadyExistException(String message) {
        super(message);
    }
}
