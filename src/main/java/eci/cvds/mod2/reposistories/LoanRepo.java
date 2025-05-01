package eci.cvds.mod2.reposistories;

import eci.cvds.mod2.modules.Loan;
import eci.cvds.mod2.util.State;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


import java.util.List;


@Repository
public interface LoanRepo extends MongoRepository<Loan, String> {
    public List<Loan> findByState(State state);
}
