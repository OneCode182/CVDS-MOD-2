package eci.cvds.mod2.reposistories;

import eci.cvds.mod2.modules.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepo extends MongoRepository<Reservation, String> {

}
