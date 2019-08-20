package tiw1.maintenance.metier;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tiw1.maintenance.models.Trottinette;

import javax.persistence.EntityManager;
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
        return em
                .createNamedQuery("trottinetteById", Trottinette.class)
                .setParameter("id", id)
                .getSingleResult();
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
}
