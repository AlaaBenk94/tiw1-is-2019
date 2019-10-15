package tiw1.emprunt.controleur;

import org.picocontainer.Startable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tiw1.emprunt.contexte.Context;
import tiw1.emprunt.model.Trottinette;
import tiw1.emprunt.model.dto.Response;
import tiw1.emprunt.persistence.EmpruntDAO;

import java.io.IOException;
import java.util.Map;

public abstract class ResourceController implements Startable, Processable {
    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private static final String ADD = "ADD";
    private static final String REMOVE = "REMOVE";
    private static final String GET = "GET";
    private static final String UPDATE ="UPDATE";

    protected static final String ID = "ID";
    protected static final String DATE = "DATE";
    protected static final String EMPRUNT = "EMPRUNT";

    protected Context context;

    public ResourceController(Context context) {
        this.context = context;
    }

    @Override
    public Response process(String method, Map<String, Object> params) throws IOException {
        if(method.toUpperCase().equals(ResourceController.GET))
            return get(params);
        if(method.toUpperCase().equals(ResourceController.ADD))
            return add(params);
        if(method.toUpperCase().equals(ResourceController.REMOVE))
            return remove(params);
        if(method.toUpperCase().equals(ResourceController.UPDATE))
            return update(params);

        return Response.create(Response.UNKNOWN_METHOD, method + " command is UNKNOWN");
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
