package tiw1.serveur;

import org.picocontainer.Startable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tiw1.emprunt.model.Emprunt;
import tiw1.emprunt.model.Trottinette;
import tiw1.emprunt.persistence.AbonneDAO;
import tiw1.emprunt.persistence.EmpruntDAO;

import java.util.List;

public class Controleur implements Startable {
    private final Logger LOG = LoggerFactory.getLogger(Controleur.class);

    private String name = "";
    private List<Trottinette> trottinetteList = null;
    private AbonneDAO abonneDAO = null;
    private EmpruntDAO empruntDAO=null;

    public Controleur( String name, List<Trottinette> trottinetteList, AbonneDAO abonneDAO,EmpruntDAO emprunt ) {
        this.name = name;
        this.trottinetteList = trottinetteList;
        this.abonneDAO = abonneDAO;
        this.empruntDAO=emprunt;
    }

    @Override
    public void start() {
        System.out.println("Composant " + this.getClass().getTypeName() + " demarre. Objet d'acces aux donnees : "
            + abonneDAO.toString());
    }

    @Override
    public void stop() {

    }
}
