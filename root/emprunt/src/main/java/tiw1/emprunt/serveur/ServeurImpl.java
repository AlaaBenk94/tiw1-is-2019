package tiw1.emprunt.serveur;

import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.parameters.ConstantParameter;
import tiw1.emprunt.model.dto.Response;
import tiw1.emprunt.persistence.AbonneDAO;
import tiw1.emprunt.persistence.EmpruntDAO;
import tiw1.emprunt.serveur.controleur.Controleur;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ServeurImpl implements Serveur{

    public static Controleur controleur = null;
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("test-pu");


    public ServeurImpl() {
        // Container setup
        MutablePicoContainer myContainer = new DefaultPicoContainer()
                .addComponent(Controleur.class)
                .addComponent(String.class)
                .addComponent(Map.class, HashMap.class)
                .addComponent(AbonneDAO.class, AbonneDAO.class, new ConstantParameter("abonnes.json"))
                .addComponent(EmpruntDAO.class, EmpruntDAO.class, new ConstantParameter(emf.createEntityManager()));

        // Getting instance & starting 'Controleur'
        ServeurImpl.controleur = myContainer.getComponent(Controleur.class);
        ServeurImpl.controleur.start();
    }

    @Override
    public Response processRequest(String commande, Map<String, Object> parametres) {
        try {
            return ServeurImpl.controleur.process(commande, parametres);
        } catch (IOException e) {
            e.printStackTrace();
            return Response.create(Response.ERROR, "There was an error when precessing the request");
        }
    }

}
