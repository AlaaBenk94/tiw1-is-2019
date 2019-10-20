package tiw1.emprunt.controleur;

import tiw1.emprunt.contexte.Annuaire;
import tiw1.emprunt.model.Trottinette;
import tiw1.emprunt.model.dto.Response;
import tiw1.emprunt.pool.TrottinettePool;

import java.util.Map;

import static tiw1.emprunt.model.dto.Constants.*;
import static tiw1.emprunt.serveur.ServeurImpl.METIER;


public class TrottinetteResource extends ResourceController {

    private TrottinettePool trottinettePool;

    public TrottinetteResource() {}

    public TrottinetteResource(Annuaire annuaire) {
        super(annuaire);
    }

    @Override
    public Response get(Map<String, Object> params) {
        try {
            if(params.size()==0)
                return Response.create(OK,"", trottinettePool.getTrottinettesList());
            if (params.containsKey(ID))
                if(params.containsKey(DISPO))
                    return Response.create(OK, "",
                            ((Trottinette) trottinettePool.getTrottinettes().get(38L)).isDisponible());
                else
                    return Response.create(OK,"", trottinettePool.getTrottinette((Long) params.get(ID)));
            }
        catch(NullPointerException e){
            e.printStackTrace();
            return Response.create(ERROR, "Invalid ID");
        }
        catch(ClassCastException e){
            e.printStackTrace();
            return Response.create(ERROR, "Invalid ID");
        }

        return Response.create(ERROR, "Unknow param");
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
        this.trottinettePool = ((TrottinettePool) annuaire.lookup(METIER
                                        + TrottinettePool.class.getSimpleName()));
    }

    @Override
    public void update() {
        this.trottinettePool = ((TrottinettePool) annuaire.lookup(METIER
                                        + TrottinettePool.class.getSimpleName()));
    }
}
