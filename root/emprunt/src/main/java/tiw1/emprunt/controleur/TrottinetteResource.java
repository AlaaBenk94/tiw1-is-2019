package tiw1.emprunt.controleur;

import tiw1.emprunt.contexte.Annuaire;
import tiw1.emprunt.model.Trottinette;
import tiw1.emprunt.model.dto.Response;

import java.util.HashMap;
import java.util.Map;

import static tiw1.emprunt.serveur.ServeurImpl.METIER;


public class TrottinetteResource extends ResourceController {

    private Map<Long, Trottinette> trottinetteList;

    public TrottinetteResource() {}

    public TrottinetteResource(Annuaire annuaire) {
        super(annuaire);
    }

    @Override
    public Response get(Map<String, Object> params) {
        try {
            if(params.size()==0)
                return Response.create(Response.OK,"", trottinetteList);
            if (params.containsKey(ID))
                return Response.create(Response.OK,"", trottinetteList.get((String) params.get(ID)));
        }
        catch(NullPointerException e){
            return Response.create(Response.ERROR, "Invalid ID");
        }
        catch(ClassCastException e){
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
        this.trottinetteList = new HashMap<>();
        this.trottinetteList.putAll((Map<Long,Trottinette>) annuaire.lookup(
                                        METIER + Trottinette.class.getSimpleName()));
    }

    @Override
    public void update() {
        this.trottinetteList.putAll((Map<Long,Trottinette>) annuaire.lookup(
                                        METIER + Trottinette.class.getSimpleName()));
    }
}
