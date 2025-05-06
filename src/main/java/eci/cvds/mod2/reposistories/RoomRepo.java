package eci.cvds.mod2.reposistories;

import eci.cvds.mod2.modules.Room;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepo extends MongoRepository<Room, String> {
    List<Room> findByBuilding(@NotNull char building);
    List<Room> findByCapacity(int capacity);
}
