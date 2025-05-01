package eci.cvds.mod2.modules;
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

}
