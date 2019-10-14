package tiw1.emprunt.serveur;

import tiw1.emprunt.model.dto.Response;

import java.util.Map;

public interface Serveur {
    Response processRequest(String commande, String method, Map<String, Object> params);
}
