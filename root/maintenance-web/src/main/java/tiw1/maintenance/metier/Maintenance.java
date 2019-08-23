package tiw1.maintenance.metier;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tiw1.maintenance.models.Trottinette;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class Maintenance {
    @PersistenceContext
    private EntityManager em;

    public List<Trottinette> getTrottinettes() {
        return em.createNamedQuery("allTrottinettes", Trottinette.class).getResultList();
    }

    public Trottinette getTrottinette(long id) {
        try {
            return em
                    .createNamedQuery("trottinetteById", Trottinette.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public Trottinette creerTrottinette() {
        Trottinette t = new Trottinette();
        em.persist(t);
        return t;
    }

    @Transactional
    public void supprimerTrottinette(Trottinette t) {
        em.remove(t);
    }

    @Transactional
    public void supprimerTrottinette(long id) {
        Trottinette t = getTrottinette(id);
        supprimerTrottinette(t);
    }

    @Transactional
    public Trottinette updateTrottinette(Trottinette t) {
        return em.merge(t);
    }
}
