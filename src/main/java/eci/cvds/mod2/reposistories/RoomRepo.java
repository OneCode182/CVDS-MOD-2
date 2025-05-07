package eci.cvds.mod2.reposistories;

import eci.cvds.mod2.modules.Room;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepo extends MongoRepository<Room, String> {
    Optional<Room> findByRoomId(String roomId);
    List<Room> findByBuilding(@NotNull char building);
    List<Room> findByCapacity(int capacity);
}
