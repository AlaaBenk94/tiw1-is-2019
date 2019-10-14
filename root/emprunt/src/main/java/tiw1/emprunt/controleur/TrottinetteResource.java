package tiw1.emprunt.controleur;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tiw1.emprunt.contexte.AbonneContext;
import tiw1.emprunt.model.Trottinette;
import tiw1.emprunt.model.dto.Response;
import tiw1.emprunt.persistence.AbonneDAO;
import tiw1.emprunt.persistence.EmpruntDAO;
import tiw1.emprunt.persistence.TrottinetteLoader;

import java.util.Map;

public class TrottinetteResource extends ResourceController {

    public TrottinetteResource(AbonneContext abonneContext, EmpruntDAO empruntDAO,
                                    Map<Long, Trottinette> trottinetteList) {
        super(abonneContext, empruntDAO, trottinetteList);
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
        super.start();
        try {
            TrottinetteLoader.load();
            this.trottinetteList = TrottinetteLoader.getTrottinettes();
        } catch (Exception e) {
            LOG.error("Can't load trottinettes : " + e.getMessage());
        }

    }
}
