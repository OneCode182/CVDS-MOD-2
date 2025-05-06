package eci.cvds.mod2.reposistories;

import eci.cvds.mod2.modules.Reservation;
import eci.cvds.mod2.util.Date;
import eci.cvds.mod2.util.Role;
import eci.cvds.mod2.util.State;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReservationRepo extends MongoRepository<Reservation, String> {


    List<Reservation>findByUserId(String userId);
    List<Reservation>findByRole(Role role);
    List<Reservation>findByDates(Date date);
    List<Reservation>findByRoom(String roomId);
    List<Reservation>findByState(State state);
    List<Reservation> findByDate(Date date);
    List<Reservation> findByRoomId(String roomId);
}
