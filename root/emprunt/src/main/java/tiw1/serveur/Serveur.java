package tiw1.serveur;

import tiw1.emprunt.model.dto.Response;

import java.util.Map;

public interface Serveur {
    public Response processRequest(String commande, Map<String, Object> parametres);
}
