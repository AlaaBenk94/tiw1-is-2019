package tiw1.emprunt.controleur;

import org.picocontainer.Startable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tiw1.emprunt.contexte.Annuaire;
import tiw1.emprunt.contexte.Observer;
import tiw1.emprunt.model.dto.Constants;
import tiw1.emprunt.model.dto.Response;

import java.io.IOException;
import java.util.Map;

import static tiw1.emprunt.model.dto.Constants.UNKNOWN_METHOD;

public abstract class ResourceController implements Startable, Processable, Observer {
    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());

    protected Annuaire annuaire;

    public ResourceController() {}

    public ResourceController(Annuaire annuaire) {
        this.annuaire = annuaire;
    }

    public void setAnnuaire(Annuaire annuaire) {
        this.annuaire = annuaire;
    }

    @Override
    public Response process(String method, Map<String, Object> params) throws IOException {
        if(method.toUpperCase().equals(Constants.GET))
            return get(params);
        if(method.toUpperCase().equals(Constants.ADD))
            return add(params);
        if(method.toUpperCase().equals(Constants.REMOVE))
            return remove(params);
        if(method.toUpperCase().equals(Constants.UPDATE))
            return update(params);

        return Response.create(UNKNOWN_METHOD, method + " command is UNKNOWN");
    }

    public abstract Response get(Map<String, Object> params);
    public abstract Response remove(Map<String, Object> params);
    public abstract Response add(Map<String, Object> params);
    public abstract Response update(Map<String, Object> params);

    @Override
    public void start() {
        LOG.info("Composant " + this.getClass().getSimpleName() + " demarre.");
    }

    @Override
    public void stop() {
        LOG.info("Composant " + this.getClass().getSimpleName() + " Stop.");
    }

}
