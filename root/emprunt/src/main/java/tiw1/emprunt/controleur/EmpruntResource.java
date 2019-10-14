package tiw1.emprunt.controleur;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tiw1.emprunt.model.Emprunt;
import tiw1.emprunt.model.Trottinette;
import tiw1.emprunt.model.dto.EmpruntDTO;
import tiw1.emprunt.model.dto.Response;
import tiw1.emprunt.persistence.AbonneDAO;
import tiw1.emprunt.persistence.EmpruntDAO;

import java.util.Date;
import java.util.Map;

public class EmpruntResource extends ResourceController {
    private final Logger LOG = LoggerFactory.getLogger(EmpruntResource.class);

    public EmpruntResource(AbonneDAO abonneDAO, EmpruntDAO empruntDAO,
                                Map<Long, Trottinette> trottinetteList) {
        super(abonneDAO, empruntDAO, trottinetteList);
    }

    @Override
    public Response get(Map<String, Object> params) {
        if (params.containsKey(DATE))
            return Response.create(Response.OK, "", empruntDAO.getByDate((Date) params.get(DATE)).get());
        if (params.containsKey(ID))
            return Response.create(Response.OK, "", empruntDAO.get((long) params.get(ID)).get());
        if (params.size()==0)
            return Response.create(Response.OK, "", empruntDAO.getAll());

        return Response.create(Response.ERROR,"Unknown param");
    }

    @Override
    public Response remove(Map<String, Object> params) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response add(Map<String, Object> params) {
       // try{
            Emprunt emprunt = new Emprunt((EmpruntDTO) params.get(EMPRUNT));
            this.empruntDAO.save(emprunt);
            return Response.create(Response.OK, "Emprunt created Successfully", emprunt);
       /* }
        catch(ClassCastException e) {
            return Response.create(Response.ERROR, "Emprunt NOT added : Bad Format");
        }*/
    }

    @Override
    public Response update(Map<String, Object> params) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void start() {
        LOG.info("Composant " + this.getClass().getTypeName() + " demarre. Objet d'acces aux donnees : "
                + this.abonneDAO.toString()
                + " | "
                + this.empruntDAO.toString()
                + " | "
                + this.trottinetteList.toString());
    }

    @Override
    public void stop() {
        LOG.info("Composant " + this.getClass().getTypeName() + " Stop. Objet d'acces aux donnees : "
                + this.empruntDAO.toString());
    }

}
