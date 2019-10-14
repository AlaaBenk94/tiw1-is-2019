package tiw1.emprunt.controleur;

import tiw1.emprunt.model.dto.Response;

import java.io.IOException;
import java.util.Map;

public interface Processable {
    Response process(String method, Map<String, Object> params) throws IOException ;
}
