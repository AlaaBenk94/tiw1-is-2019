package tiw1.emprunt.serveur;

import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.behaviors.Caching;
import org.picocontainer.injectors.AdaptingInjection;
import org.picocontainer.parameters.ConstantParameter;
import tiw1.emprunt.contexte.AbonneContext;
import tiw1.emprunt.contexte.AbonneContextImpl;
import tiw1.emprunt.controleur.AbonneResource;
import tiw1.emprunt.controleur.Controleur;
import tiw1.emprunt.controleur.EmpruntResource;
import tiw1.emprunt.controleur.TrottinetteResource;
import tiw1.emprunt.model.dto.Response;
import tiw1.emprunt.persistence.AbonneDAO;
import tiw1.emprunt.persistence.EmpruntDAO;
import static org.picocontainer.Characteristics.CACHE;
import static org.picocontainer.Characteristics.SDI;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ServeurImpl implements Serveur{

    public static Controleur contoleurMaster;
    public static AbonneContext abonneContext;

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("test-pu");


    public ServeurImpl() {
        // Container setup
        MutablePicoContainer myContainer = new DefaultPicoContainer(new Caching())
                .addComponent(AbonneResource.class)
                .addComponent(EmpruntResource.class)
                .addComponent(TrottinetteResource.class)
                .addComponent(Controleur.class)
                .addComponent(String.class)
                .addComponent(Map.class, HashMap.class)
                .as(SDI).addComponent(AbonneContext.class, AbonneContextImpl.class)
                .addComponent(AbonneDAO.class, AbonneDAO.class, new ConstantParameter("abonnes.json"))
                .addComponent(EmpruntDAO.class, EmpruntDAO.class, new ConstantParameter(emf.createEntityManager()));

        // Getting instance
        contoleurMaster = myContainer.getComponent(Controleur.class);
        abonneContext = myContainer.getComponent(AbonneContext.class);

        // Starting instances
        myContainer.start();

    }

    @Override
    public Response processRequest(String commande, String method, Map<String, Object> params) {
        return contoleurMaster.forwardRequest(commande, method, params);
    }
}
