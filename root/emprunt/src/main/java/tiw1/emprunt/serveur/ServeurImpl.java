package tiw1.emprunt.serveur;

import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.parameters.ConstantParameter;
import tiw1.emprunt.controleur.AbonneResource;
import tiw1.emprunt.controleur.EmpruntResource;
import tiw1.emprunt.controleur.TrottinetteResource;
import tiw1.emprunt.model.dto.Response;
import tiw1.emprunt.persistence.AbonneDAO;
import tiw1.emprunt.persistence.EmpruntDAO;
import tiw1.emprunt.controleur.Controleur;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ServeurImpl implements Serveur{

    private static final String ABONNE = "ABONNE";
    private static final String TROTINETTE = "TROTINETTE";
    private static final String EMPRUNT = "EMPRUNT";

    public static Controleur controleur = null;
    public static AbonneResource abonneResource=null;
    public static EmpruntResource empruntResource=null;
    public static TrottinetteResource trottinetteResource=null;

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("test-pu");


    public ServeurImpl() {
        // Container setup
        MutablePicoContainer myContainer = new DefaultPicoContainer()
                .addComponent(AbonneResource.class)
                .addComponent(EmpruntResource.class)
                .addComponent(TrottinetteResource.class)
                .addComponent(Controleur.class)
                .addComponent(String.class)
                .addComponent(Map.class, HashMap.class)
                .addComponent(AbonneDAO.class, AbonneDAO.class, new ConstantParameter("abonnes.json"))
                .addComponent(EmpruntDAO.class, EmpruntDAO.class, new ConstantParameter(emf.createEntityManager()));

        // Getting instance & starting 'Controleur'
        //ServeurImpl.controleur = myContainer.getComponent(Controleur.class);
        ServeurImpl.abonneResource=myContainer.getComponent(AbonneResource.class);
        ServeurImpl.empruntResource=myContainer.getComponent(EmpruntResource.class);
        ServeurImpl.trottinetteResource=myContainer.getComponent(TrottinetteResource.class);

        ServeurImpl.abonneResource.start();
        ServeurImpl.trottinetteResource.start();
        ServeurImpl.empruntResource.start();
    }

    @Override
    public Response processRequest(String ressource,String commande, Map<String, Object> parametres) {
        try {
            if (ressource.toUpperCase() == ServeurImpl.TROTINETTE)
                trottinetteResource.process(commande, parametres);
            else if(ressource.toUpperCase() == ServeurImpl.ABONNE)
                abonneResource.process(commande,parametres);
            else if(ressource.toUpperCase() == ServeurImpl.EMPRUNT)
                empruntResource.process(commande,parametres);

        }catch (IOException e) {
            e.printStackTrace();
        }
        return Response.create(Response.ERROR, "There was an error when precessing the request");
    }

}
