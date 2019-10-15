package tiw1.emprunt.serveur;

import tiw1.emprunt.contexte.Annuaire;
import tiw1.emprunt.model.dto.Response;

import java.util.Map;

public interface Serveur {
    /**
     * Methode de service de Serveur
     * @param commande la ressource
     * @param method la methode a appeler dans la ressource
     * @param params les parametres de la methode
     * @return
     */
    Response processRequest(String commande, String method, Map<String, Object> params);

    Annuaire getAnnuaire();
}
