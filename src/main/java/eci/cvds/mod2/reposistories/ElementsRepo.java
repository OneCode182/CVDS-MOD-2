package eci.cvds.mod2.reposistories;

import eci.cvds.mod2.modules.RecreationalElements;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElementsRepo extends MongoRepository<RecreationalElements, String > {
}
