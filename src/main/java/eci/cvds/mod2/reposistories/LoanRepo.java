package eci.cvds.mod2.reposistories;

import eci.cvds.mod2.modules.Loan;
import eci.cvds.mod2.util.State;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepo extends MongoRepository<Loan, String> {

    List<Loan>findByState(State state);
}
