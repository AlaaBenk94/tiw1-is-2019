package tiw1.emprunt.serveur;

import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.behaviors.Caching;
import org.picocontainer.parameters.ConstantParameter;
import tiw1.emprunt.controleur.AbonneResource;
import tiw1.emprunt.controleur.EmpruntResource;
import tiw1.emprunt.controleur.TrottinetteResource;
import tiw1.emprunt.model.dto.Response;
import tiw1.emprunt.persistence.AbonneDAO;
import tiw1.emprunt.persistence.EmpruntDAO;
import tiw1.emprunt.controleur.ControleurImpl;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ServeurImpl implements Serveur{

    private static final String ABONNE = "ABONNE";
    private static final String TROTINETTE = "TROTINETTE";
    private static final String EMPRUNT = "EMPRUNT";

    public static AbonneResource abonneResource=null;
    public static EmpruntResource empruntResource=null;
    public static TrottinetteResource trottinetteResource=null;

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("test-pu");


    public ServeurImpl() {
        // Container setup
        MutablePicoContainer myContainer = new DefaultPicoContainer(new Caching())
                .addComponent(AbonneResource.class)
                .addComponent(EmpruntResource.class)
                .addComponent(TrottinetteResource.class)
                .addComponent(ControleurImpl.class)
                .addComponent(String.class)
                .addComponent(Map.class, HashMap.class)
                .addComponent(AbonneDAO.class, AbonneDAO.class, new ConstantParameter("abonnes.json"))
                .addComponent(EmpruntDAO.class, EmpruntDAO.class, new ConstantParameter(emf.createEntityManager()));

        // Getting instance
        ServeurImpl.abonneResource=myContainer.getComponent(AbonneResource.class);
        ServeurImpl.empruntResource=myContainer.getComponent(EmpruntResource.class);
        ServeurImpl.trottinetteResource=myContainer.getComponent(TrottinetteResource.class);

        // Starting instances
        myContainer.start();

    }


    /**
     * Permet de transferer la requete au bon controleur de ressource.
     * @param commande nom de la ressource
     * @param method la methode a appeler dans la ressource
     * @param params les parametres de la methode
     * @return un objet reponse qui encapsule le contenue
     */
    private Response forwardRequest(String commande, String method, Map<String, Object> params) {
        try {
            if (commande.toUpperCase() == ServeurImpl.TROTINETTE)
                return trottinetteResource.process(method, params);
            if(commande.toUpperCase() == ServeurImpl.ABONNE)
                return abonneResource.process(method,params);
            if(commande.toUpperCase() == ServeurImpl.EMPRUNT)
                return empruntResource.process(method,params);
            return Response.create(Response.UNKNOWN_COMMAND, "Unknown resource name");

        }catch (IOException e) {
            e.printStackTrace();
        }
        return Response.create(Response.ERROR, "There was an error when precessing the request");
    }

    @Override
    public Response processRequest(String commande, String method, Map<String, Object> params) {
        return this.forwardRequest(commande, method, params);
    }
}
