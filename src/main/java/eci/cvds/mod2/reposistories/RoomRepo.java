package eci.cvds.mod2.reposistories;

import eci.cvds.mod2.modules.Room;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepo extends MongoRepository<Room, String> {
    List<Room>findByBuilding(String building);
    List<Room>findByCapacity(int capacity);
}
