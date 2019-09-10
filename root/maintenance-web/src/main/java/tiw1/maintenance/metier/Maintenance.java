package tiw1.maintenance.metier;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tiw1.maintenance.models.Intervention;
import tiw1.maintenance.models.Trottinette;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class Maintenance {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public List<Trottinette> getTrottinettes() {
        List<Trottinette> trottinettes = em.createNamedQuery("allTrottinettes", Trottinette.class).getResultList();
        // ensure trottinettes are fully fetched, including associated Intervention entities
        for (Trottinette t : trottinettes) {
            t.getInterventions().size();
        }
        return trottinettes;
    }

    @Transactional
    public Trottinette getTrottinetteAndInterventions(long id) {
        try {
            Trottinette t = em
                    .createNamedQuery("trottinetteById", Trottinette.class)
                    .setParameter("id", id)
                    .getSingleResult();
            // ensure trottinettes is fully fetched, including associated Intervention entities
            t.getInterventions().size();
            return t;
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
        Trottinette t = getTrottinetteAndInterventions(id);
        supprimerTrottinette(t);
    }

    @Transactional
    public Trottinette updateTrottinette(Trottinette t) {
        Trottinette t2 = em.merge(t);
        // ensure trottinettes is fully fetched, including associated Intervention entities
        t2.getInterventions().size();
        return t2;
    }

    @Transactional
    public Trottinette ajouterIntervention(long idTrottinette, Intervention intervention) {
        // TODO: tests: cas standard, trottinette inexistante
        Trottinette t = em.find(Trottinette.class, idTrottinette);
        if (t != null) {
            em.persist(intervention);
            t.ajouterIntervention(intervention);
        }
        return t;
    }
}
