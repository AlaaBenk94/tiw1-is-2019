package tiw1.serveur;

import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.Parameter;
import org.picocontainer.PicoBuilder;
import tiw1.emprunt.model.Abonne;
import tiw1.emprunt.model.Emprunt;
import tiw1.emprunt.model.Trottinette;
import tiw1.emprunt.persistence.AbonneDAO;
import tiw1.emprunt.persistence.DAO;
import tiw1.emprunt.persistence.EmpruntDAO;
import tiw1.emprunt.persistence.TrottinetteLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Serveur {

    // TODO : delete deprecated attributes
    private DAO<Abonne> abonneDAO;
    private DAO<Emprunt> empruntDAO;

    public static Controleur controleur = null;


    public Serveur() {
        // Container setup
        MutablePicoContainer myContainer = new DefaultPicoContainer()
                .addComponent(String.class)
                .addComponent(ArrayList.class)
                .addComponent(AbonneDAO.class)
                .addComponent(Controleur.class)
                .addComponent(Emprunt.class)
                .addComponent(EmpruntDAO.class);

        // Getting instance & starting 'Controleur'
        Serveur.controleur = myContainer.getComponent(Controleur.class);
        Serveur.controleur.start();
    }

    // TODO : delete this constructor
    Serveur(AbonneDAO abonneDAO,EmpruntDAO empruntDAO) throws IOException {
        this.abonneDAO = abonneDAO;
        this.empruntDAO = empruntDAO;
    }

    /* Gestionnaire des abonnees */
    public void abonnement(Abonne abonne) throws Exception {
        abonneDAO.save(abonne);
     }

    public void desabonnement(Abonne abonne) throws Exception {
        abonneDAO.delete(abonne);
    }

    /* Gestionnaire des trotinettes */
    public void loadTrotinettes() throws Exception{
        TrottinetteLoader.load();
    }

    public boolean verifyAvailiblity(long id) throws NullPointerException{
        //loadTrotinettes();
        Map<Long, Trottinette> trottinettes =TrottinetteLoader.getTrottinettes();
        //Null pointer Exception
        return trottinettes.get(id).isDisponible();
    }

    //Gestion des Emprunt
    public void addEmprunt(Emprunt emp) throws Exception{
        empruntDAO.save(emp);
    }

    public static Controleur getControleur() {
        return Serveur.controleur;
    }

}
