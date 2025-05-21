package eci.cvds.mod2.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public enum Role {
    SALA_ADMIN, STUDENT;
    public static Role fromString(String roleStr) {
        try {
            return Role.valueOf(roleStr.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rol inválido: " + roleStr);
        }
    }


}
