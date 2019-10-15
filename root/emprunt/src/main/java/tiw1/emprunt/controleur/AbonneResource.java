package tiw1.emprunt.controleur;

import tiw1.emprunt.contexte.Annuaire;
import tiw1.emprunt.model.Abonne;
import tiw1.emprunt.model.dto.Response;
import tiw1.emprunt.persistence.AbonneDAO;

import java.io.IOException;
import java.util.Map;

import static tiw1.emprunt.serveur.ServeurImpl.PERSISTENCE;

public class AbonneResource extends ResourceController {
    private final static String ABONNE ="ABONNE";
    private final static String ID="ID";

    private AbonneDAO abonneDAO;

    public AbonneResource(Annuaire annuaire) {
        super(annuaire);
    }

    @Override
    public void start() {
        super.start();
        abonneDAO = (AbonneDAO) annuaire.lookup(PERSISTENCE
                            + AbonneDAO.class.getSimpleName().toLowerCase());

        System.out.println(PERSISTENCE + AbonneDAO.class.getSimpleName().toLowerCase()
                            + " == " + this.annuaire + " == " + abonneDAO );

    }

    @Override
    public Response get(Map<String, Object> params) {

            if(params.containsKey(AbonneResource.ID))
                try {
                    return Response.create(Response.OK, "", this.abonneDAO.get((long) params.get(AbonneResource.ID)));
                }
                catch (ClassCastException e) {
                    return Response.create(Response.ERROR, "Invalid argument value");
                }
            if (params.size()==0)
                return Response.create(Response.OK,"",this.abonneDAO.getAll());

            return Response.create(Response.ERROR,"Unknown param");
    }

    @Override
    public Response remove(Map<String, Object> params) {
        try {
            this.abonneDAO.delete((Abonne) params.get(AbonneResource.ABONNE));
            return Response.create(Response.OK, "Abonnee removed successfuly");
        } catch (IOException e) {
            LOG.error(e.getMessage());
            return Response.create(Response.ERROR, "Abonnee has NOT been removed");
        }
    }

    @Override
    public Response add(Map<String, Object> params) {
        try {
            this.abonneDAO.save((Abonne) params.get(AbonneResource.ABONNE));
                return Response.create(Response.OK, "Abonnee added successfuly");
        } catch (IOException e) {
            LOG.error(e.getMessage());
            return Response.create(Response.ERROR, "Abonnee NOT added");
        }
    }

    @Override
    public Response update(Map<String, Object> params) {
        try {
            this.abonneDAO.update((Abonne) params.get(AbonneResource.ABONNE));
            return Response.create(Response.OK, "Abonnee updated successfuly");
        } catch (IOException e) {
            LOG.error(e.getMessage());
            return Response.create(Response.ERROR, "Abonnee NOT updated");
        }
    }
}
