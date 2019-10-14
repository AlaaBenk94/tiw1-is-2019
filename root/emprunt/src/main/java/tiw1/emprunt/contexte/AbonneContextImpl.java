package tiw1.emprunt.contexte;

import org.picocontainer.Startable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tiw1.emprunt.controleur.EmpruntResource;
import tiw1.emprunt.persistence.AbonneDAO;

public class AbonneContextImpl implements AbonneContext, Startable {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    AbonneDAO abonneDAO;

    @Override
    public void setAbonneDAO(AbonneDAO abonneDAO) {
        this.abonneDAO = abonneDAO;
    }

    @Override
    public AbonneDAO getAbonneDAO() {
        return this.abonneDAO;
    }

    @Override
    public void start() {
        LOG.info("Composant " + this.getClass().getTypeName() + " demarre. Objet d'acces aux donnees : "
                + this.abonneDAO.toString());
    }

    @Override
    public void stop() {

    }
}
