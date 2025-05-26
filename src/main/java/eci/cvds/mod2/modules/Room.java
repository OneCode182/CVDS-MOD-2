package eci.cvds.mod2.modules;
import io.swagger.v3.oas.annotations.media.Schema;
import eci.cvds.mod2.exceptions.RoomAlreadyExistException;
import eci.cvds.mod2.exceptions.RoomException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document(collection = "rooms")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Representa una sala crea de Bienestar Universitario.")
public class Room {
    @Id
    @NotBlank
    @Schema(description = "ID único de la Sala", example = "642b7f1acb01j2000154d4")
    String roomId;
    @NotNull
    @Schema(description = "Edificio donde se encuentra la sala", example = "A")
    char building;
    @Positive
    @Schema(description = "Capacidad de la sala", example = "20")
    int capacity;
    private Set<String> elementList = new HashSet<>();

    public void removeElement(String elementId) {
        if (!elementList.contains(elementId)) {
            throw new IllegalArgumentException("Element ID not found in room");
        }
        elementList.remove(elementId);
    }


    public void addElement(String elementId) {
        elementList.add(elementId);
    }
    public Set<String> getElementList() {
        return new HashSet<>(elementList);
    }
    public void reduceCapacity(int people){
        if(capacity-people>=0){
            capacity-=people;
        }else {
            throw new RoomAlreadyExistException(RoomException.QUANTITY_CANNOT_BE_LOWER_THAN_0);
        }
    }
    public void increaseCapacity(int people){
        capacity+=people;
    }

}
