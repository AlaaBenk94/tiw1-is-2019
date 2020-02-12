package tiw1.repository;

import org.springframework.data.repository.CrudRepository;
import tiw1.domain.Emprunt;

import java.sql.Timestamp;
import java.util.List;

public interface EmpruntRepository extends CrudRepository<Emprunt, Long> {

    Emprunt findEmpruntByActivationNumber(String activationNumber);

//    //    @Query("from Emprunt e where not e.activated")
//    List<Emprunt> findAllByActivatedFalseAndDateBefore();

    List<Emprunt> deleteAllByActivatedFalseAndDateBefore(Timestamp date);

}
