package tiw1.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tiw1.domain.Abonne;

import java.util.Optional;

@Repository
public interface AbonneRepository extends CrudRepository<Abonne, Long> {

    Optional<Abonne> findAbonneByName(String name);

}
