package eci.cvds.mod2.reposistories;

import eci.cvds.mod2.modules.Loan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepo extends MongoRepository<Loan, String> {
}
