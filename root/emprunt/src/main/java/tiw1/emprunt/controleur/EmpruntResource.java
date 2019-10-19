package tiw1.emprunt.controleur;

import tiw1.emprunt.contexte.Annuaire;
import tiw1.emprunt.model.Emprunt;
import tiw1.emprunt.model.dto.EmpruntDTO;
import tiw1.emprunt.model.dto.Response;
import tiw1.emprunt.persistence.EmpruntDAO;

import java.util.Date;
import java.util.Map;

import static tiw1.emprunt.model.dto.Constants.ERROR;
import static tiw1.emprunt.model.dto.Constants.OK;
import static tiw1.emprunt.serveur.ServeurImpl.PERSISTENCE;

public class EmpruntResource extends ResourceController {

    private EmpruntDAO empruntDAO;

    public EmpruntResource() {}

    public EmpruntResource(Annuaire annuaire) {
        super(annuaire);
    }

    //@Startable(Object componenet,String Key,Callback , );
    @Override
    public void start() {
        super.start();
        empruntDAO = (EmpruntDAO) this.annuaire.lookup(PERSISTENCE + EmpruntDAO.class.getSimpleName());
    }

    @Override
    public Response get(Map<String, Object> params) {
        if (params.containsKey(DATE))
            return Response.create(OK, "",
                    empruntDAO.getByDate((Date) params.get(DATE)).get());
        if (params.containsKey(ID))
            return Response.create(OK, "",
                    new EmpruntDTO((Emprunt) empruntDAO.get((long) params.get(ID)).get()));
        if (params.size()==0)
            return Response.create(OK, "",
                    empruntDAO.getAll());
        return Response.create(ERROR,"Unknown param");
    }

    @Override
    public Response remove(Map<String, Object> params) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response add(Map<String, Object> params) {
        Emprunt emprunt = new Emprunt((EmpruntDTO) params.get(EMPRUNT));
        this.empruntDAO.save(emprunt);
        return Response.create(OK, "Emprunt created Successfully",
                new EmpruntDTO(emprunt));
    }

    @Override
    public Response update(Map<String, Object> params) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update() {
        empruntDAO = (EmpruntDAO) this.annuaire.lookup(PERSISTENCE + EmpruntDAO.class.getSimpleName());
    }
}
