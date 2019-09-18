package tiw1.emprunt.persistence;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Source https://www.baeldung.com/java-dao-pattern
 * @param <T> Le type d'objet pour lequel c'est un DAO
 */
public interface DAO<T> {

    Optional<T> get(long id);

    List<T> getAll();

    void save(T t) throws Exception;

    void update(T t) throws Exception;

    void delete(T t) throws Exception;
}