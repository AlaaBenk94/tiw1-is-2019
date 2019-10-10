package tiw1.emprunt.persistence;

import tiw1.emprunt.model.Emprunt;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Date;

public class EmpruntDAO implements DAO<Emprunt> {
    @PersistenceContext
    private EntityManager em;

    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Emprunt> get(long id) {
        try {
            Emprunt t = em
                    .createNamedQuery("empruntById", Emprunt.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return Optional.ofNullable(t);
        } catch (NoResultException e) {
            return null;
        }
    }

    public Optional<List<Emprunt>> getByDate(Date date) {
        try {
            List<Emprunt> t = em
                    .createNamedQuery("empruntByDate", Emprunt.class)
                    .setParameter("date", date)
                    .getResultList();
            return Optional.ofNullable(t);
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Emprunt> getAll() {
        List<Emprunt> emprunts = em.createNamedQuery("allEmprunts", Emprunt.class).getResultList();
        return emprunts;
    }

    @Override
    public void save(Emprunt emprunt) {
        em.getTransaction().begin();
        if (emprunt.getId() != null) {
            em.merge(emprunt);
        } else {
            em.persist(emprunt);
        }
        em.getTransaction().commit();
    }

    @Override
    public void update(Emprunt emprunt) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Emprunt emprunt) {
        throw new UnsupportedOperationException();
    }
}
