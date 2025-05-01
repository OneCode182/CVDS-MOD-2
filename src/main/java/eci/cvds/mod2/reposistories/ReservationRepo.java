package eci.cvds.mod2.reposistories;

import eci.cvds.mod2.modules.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepo extends MongoRepository<Reservation, String> {
     List<Reservation> getReservationByUserId(String userId);

}
