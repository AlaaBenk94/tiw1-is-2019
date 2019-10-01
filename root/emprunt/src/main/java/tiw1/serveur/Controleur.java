package tiw1.serveur;

import org.picocontainer.Startable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tiw1.emprunt.model.Trottinette;
import tiw1.emprunt.persistence.AbonneDAO;

import java.util.List;

public class Controleur implements Startable {
    private final Logger LOG = LoggerFactory.getLogger(Controleur.class);

    private String name = "";
    private List<Trottinette> trottinetteList = null;
    private AbonneDAO abonneDAO = null;

    public Controleur( String name, List<Trottinette> trottinetteList, AbonneDAO abonneDAO ) {
        this.name = name;
        this.trottinetteList = trottinetteList;
        this.abonneDAO = abonneDAO;
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
