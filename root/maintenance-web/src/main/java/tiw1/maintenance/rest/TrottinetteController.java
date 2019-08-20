package tiw1.maintenance.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tiw1.maintenance.models.Trottinette;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@RestController()
public class TrottinetteController {

    @PersistenceContext
    private EntityManager em;

    @RequestMapping("/trottinette")
    public List<Trottinette> getTrottinettes() {
        return em.createNamedQuery("allTrottinettes", Trottinette.class).getResultList();
    }
}
