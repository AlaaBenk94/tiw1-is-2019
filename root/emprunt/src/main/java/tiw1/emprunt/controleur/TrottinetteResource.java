package tiw1.emprunt.controleur;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tiw1.emprunt.model.Trottinette;
import tiw1.emprunt.model.dto.Response;
import tiw1.emprunt.persistence.TrottinetteLoader;

import java.util.Map;

public class TrottinetteResource extends ControlersStartable{

    private Map<Long, Trottinette> trottinetteList = null;
    private final Logger LOG = LoggerFactory.getLogger(TrottinetteResource.class);

    public TrottinetteResource(){
        try {
            TrottinetteLoader.load();
            this.trottinetteList = TrottinetteLoader.getTrottinettes();
        } catch (Exception e) {
            LOG.error("Can't load trottinettes : " + e.getMessage());
        }
    }


    @Override
    public Response get(Map<String, Object> params) {
        try {
            if (params.containsKey(ID))
                return Response.create(Response.OK, trottinetteList.get(params.get(ID)).isDisponible() + "");
        }
        catch(NullPointerException e){
            return Response.create(Response.ERROR, "Invalid ID");
        }
        return Response.create(Response.ERROR, "Unknow param");

    }

    @Override
    public Response remove(Map<String, Object> params) {
        return null;
    }

    @Override
    public Response add(Map<String, Object> params) {
        return null;
    }

    @Override
    public Response update(Map<String, Object> params) {
        return null;
    }

    @Override
    public void start() {
        LOG.info("Composant " + this.getClass().getTypeName() + " demarre. Objet d'acces aux donnees : "
                + TrottinetteLoader.class.toString());
    }

    @Override
    public void stop() {
        LOG.info("Composant " + this.getClass().getTypeName() + " Stop. Objet d'acces aux donnees : "
                + TrottinetteLoader.class.toString());
    }
}
