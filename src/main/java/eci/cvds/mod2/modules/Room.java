package eci.cvds.mod2.modules;
import eci.cvds.mod2.exceptions.ReservationException;
import eci.cvds.mod2.exceptions.ReservationNotFoundException;
import eci.cvds.mod2.exceptions.RoomAlreadyExistException;
import eci.cvds.mod2.exceptions.RoomException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
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
public class Room {
    @NotBlank
    String roomId;
    @NotNull
    char building;
    @Positive
    int capacity;
    private Set<String> elementList = new HashSet<>();

    public void removeElement(String elementId){
        if (!elementList.remove(elementId)) {
            throw new IllegalArgumentException("Element ID not found in room");
        }
    }


    public void addElement(String elementId) {
        elementList.add(elementId);
    }
    public Set<String> getElementList() {
        return new HashSet<>(elementList);
    }
    public void reduceCapacity(){
        if(capacity>0){
            capacity-=1;
        }else {
            throw new RoomAlreadyExistException(RoomException.QUANTITY_CANNOT_BE_LOWER_THAN_0);
        }
    }
    public void increaseCapacity(){
        capacity+=1;
    }

}
