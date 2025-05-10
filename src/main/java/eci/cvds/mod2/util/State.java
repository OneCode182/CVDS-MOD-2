package eci.cvds.mod2.util;

public enum State {
    RESERVA_CONFIRMADA,
    RESERVA_CANCELADA,
    RESERVA_TERMINADA,
    PRESTAMO_DEVUELTO,
    PRESTAMO_PENDIENTE,
    DAMAGE_LOAN;
    public static boolean isValidState(State state) {
        for (State validState : State.values()) {
            if (validState == state) {
                return true;
            }
        }
        return false;
    }
}

