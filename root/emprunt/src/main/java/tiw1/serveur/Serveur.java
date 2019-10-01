package tiw1.serveur;

import tiw1.emprunt.model.Abonne;
import tiw1.emprunt.model.Emprunt;
import tiw1.emprunt.model.Trottinette;
import tiw1.emprunt.persistence.AbonneDAO;
import tiw1.emprunt.persistence.DAO;
import tiw1.emprunt.persistence.EmpruntDAO;
import tiw1.emprunt.persistence.TrottinetteLoader;

import java.io.IOException;
import java.util.Map;

public class Serveur {

    private DAO<Abonne> abonneDAO;
    private DAO<Emprunt> empruntDAO;

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

    public boolean verifyAvailiblity(long id){
        //loadTrotinettes();
        Map<Long, Trottinette> trottinettes =TrottinetteLoader.getTrottinettes();
        return trottinettes.get(id)==null?false:true;
    }

    /* Gestion des Emprunt */
    public void addEmprunt(Emprunt emp) throws Exception{
        empruntDAO.save(emp);
    }
    
}
