package eci.cvds.mod2.exceptions;

public class RoomException {
    public static final String ROOM_NOT_FOUND = "The room searched was not found";
    public  static final String ROOM_ALREADY_EXIST = "The room you tried to create already exists";
    public static final  String QUANTITY_CANNOT_BE_LOWER_THAN_0 = "Cannot book another reservation the room is full";
}
