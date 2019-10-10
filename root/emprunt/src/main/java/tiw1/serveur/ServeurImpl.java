package tiw1.serveur;

import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.parameters.ConstantParameter;
import tiw1.emprunt.model.Emprunt;
import tiw1.emprunt.model.dto.Response;
import tiw1.emprunt.persistence.AbonneDAO;
import tiw1.emprunt.persistence.DAO;
import tiw1.emprunt.persistence.EmpruntDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class ServeurImpl implements Serveur{

    public static Controleur controleur = null;

    public ServeurImpl() {
        // Container setup
        MutablePicoContainer myContainer = new DefaultPicoContainer()
                .addComponent(String.class)
                .addComponent(ArrayList.class)
                .addComponent(DAO.class, AbonneDAO.class, new ConstantParameter("abonnes.json"))
                .addComponent(Controleur.class)
                .addComponent(Emprunt.class)
                .addComponent(EmpruntDAO.class);

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
