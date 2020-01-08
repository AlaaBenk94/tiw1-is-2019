package tiw1.repository;

import org.springframework.data.repository.CrudRepository;
import tiw1.domain.Emprunt;

public interface EmpruntRepository extends CrudRepository<Emprunt, Long> {
}
