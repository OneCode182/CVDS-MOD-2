package eci.cvds.mod2.reposistories;

import eci.cvds.mod2.modules.Room;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepo extends MongoRepository<Room, String> {
}
