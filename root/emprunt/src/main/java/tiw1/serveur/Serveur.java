package tiw1.serveur;

import tiw1.emprunt.model.Abonne;
import tiw1.emprunt.persistence.AbonneDAO;
import tiw1.emprunt.persistence.DAO;

import java.io.IOException;

public class Serveur {

    private DAO<Abonne> abonneDAO;

    Serveur(AbonneDAO abonneDAO) throws IOException {
        this.abonneDAO = abonneDAO;
    }

    // Gestionnaire des abonnees
    public void abonnement(Abonne abonne) throws Exception {
        abonneDAO.save(abonne);
     }

    public void desabonnement(Abonne abonne) throws Exception {
        abonneDAO.delete(abonne);
    }

}
