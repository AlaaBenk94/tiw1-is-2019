package tiw1.emprunt.controleur;

import tiw1.emprunt.contexte.Context;
import tiw1.emprunt.model.Abonne;
import tiw1.emprunt.model.Trottinette;
import tiw1.emprunt.model.dto.Response;
import tiw1.emprunt.persistence.AbonneDAO;
import tiw1.emprunt.persistence.EmpruntDAO;

import java.io.IOException;
import java.util.Map;

public class AbonneResource extends ResourceController {
    private final static String ABONNE ="ABONNE";
    private final static String ID="ID";

    private AbonneDAO abonneDAO;

    public AbonneResource(Context context) {
        super(context);
    }

    @Override
    public void start() {
        super.start();
        abonneDAO = (AbonneDAO) context.getReference(AbonneDAO.class.getSimpleName());
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
            System.out.println("Context :: " + this.context.getReference(this.getClass().getSimpleName()) + " Abonne DAO :: " + this.abonneDAO);
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
