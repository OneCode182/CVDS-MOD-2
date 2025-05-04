package eci.cvds.mod2.modules;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "rooms")
@Getter
@Setter
public class Room {

    @Id
    private String roomId;

    private final String location;
    private String building;
    private int capacity;
    private int currentPeople;

    public Room(String roomId, String location, String building, int capacity, int currentPeople) {
        this.roomId = roomId;
        this.location = location;
        this.building = building;
        this.capacity = capacity;
        this.currentPeople = currentPeople;
    }

    public void setCurrentPeople(int currentPeople) {
        this.currentPeople = currentPeople;
    }

    public String getRoomId() {
        return roomId;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCurrentPeople() {
        return currentPeople;
    }

    public String getLocation() {
        return location;
    }

    public String getRoomDetails() {
        return "Room ID: " + roomId + ", Location: " + location + ", Building: " + building + ", Capacity: " + capacity + ", Current People: " + currentPeople;
    }

}
