package tiw1.emprunt.controleur;

import tiw1.emprunt.model.dto.Response;

import java.io.IOException;
import java.util.Map;

public interface ProcessResp {
    Response process(String cmd, Map<String, Object> params) throws IOException ;
}
