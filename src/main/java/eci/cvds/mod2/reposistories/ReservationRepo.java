package eci.cvds.mod2.reposistories;

import eci.cvds.mod2.modules.Reservation;
import eci.cvds.mod2.util.Date;
import eci.cvds.mod2.util.State;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepo extends MongoRepository<Reservation, String> {
     List<Reservation> findReservationByUserId(String userId);
     List<Reservation> findReservationByDate(Date date);
     List<Reservation> findReservationByRoomId(String roomId);
     List<Reservation> findReservationByState(State state);
     Optional<Reservation> findReservationByRoomIdAndDateAndUserId(String roomId, Date date, String userId);
}
