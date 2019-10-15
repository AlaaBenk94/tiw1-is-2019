package tiw1.emprunt.serveur;

import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.behaviors.Caching;
import org.picocontainer.parameters.ConstantParameter;
import tiw1.emprunt.contexte.Context;
import tiw1.emprunt.contexte.ContextImpl;
import tiw1.emprunt.controleur.AbonneResource;
import tiw1.emprunt.controleur.Controleur;
import tiw1.emprunt.controleur.EmpruntResource;
import tiw1.emprunt.controleur.TrottinetteResource;
import tiw1.emprunt.model.Trottinette;
import tiw1.emprunt.model.dto.Response;
import tiw1.emprunt.persistence.AbonneDAO;
import tiw1.emprunt.persistence.EmpruntDAO;
import tiw1.emprunt.persistence.TrottinetteLoader;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

import static org.picocontainer.Characteristics.*;

public class ServeurImpl implements Serveur{

    public static Controleur contoleurMaster;
    public static Context context;
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("test-pu");


    public ServeurImpl() {

        // Container setup
        MutablePicoContainer myContainer = new DefaultPicoContainer(new Caching())
                .addComponent(AbonneResource.class)
                .addComponent(EmpruntResource.class)
                .addComponent(TrottinetteResource.class)
                .addComponent(Controleur.class)
                .addComponent(String.class)
                .addComponent(EntityManager.class, emf.createEntityManager())
                .addComponent(AbonneDAO.class, AbonneDAO.class, new ConstantParameter("abonnes.json"))
                .addComponent(ContextImpl.class)
                .addComponent(EmpruntDAO.class)
                .as(NO_CACHE).addComponent(Map.class, HashMap.class);

        // Context setup
        context = myContainer.getComponent(Context.class);
        context.setReference(EntityManager.class.getSimpleName(), myContainer.getComponent(EntityManager.class));
        context.setReference(AbonneDAO.class.getSimpleName(), myContainer.getComponent(AbonneDAO.class));
        context.setReference(EmpruntDAO.class.getSimpleName(), myContainer.getComponent(EmpruntDAO.class));
        context.setReference(Trottinette.class.getSimpleName(), loadTrottinette());

        // Getting instance
        contoleurMaster = myContainer.getComponent(Controleur.class);

        // Starting instances
        myContainer.start();

    }

    private Map<Long, Trottinette> loadTrottinette() {
        try {
            TrottinetteLoader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TrottinetteLoader.getTrottinettes();
    }

    @Override
    public Response processRequest(String commande, String method, Map<String, Object> params) {
        return contoleurMaster.forwardRequest(commande, method, params);
    }
}
