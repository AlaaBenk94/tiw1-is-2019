package tiw1.serveur;

import org.picocontainer.Startable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tiw1.emprunt.model.Abonne;
import tiw1.emprunt.model.Emprunt;
import tiw1.emprunt.model.Trottinette;
import tiw1.emprunt.model.dto.Response;
import tiw1.emprunt.persistence.AbonneDAO;
import tiw1.emprunt.persistence.EmpruntDAO;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Controleur implements Startable {
    private static final String ADD = "ADD";
    private static final String REMOVE = "REMOVE";
    private static final String GET = "GET";

    private static final String ABONNE = "ABONNE";
    private static final String ID = "ID";
    private static final String EMPRUNT = "EMPRUNT";

    private final Logger LOG = LoggerFactory.getLogger(Controleur.class);

    private String name = "";
    private List<Trottinette> trottinetteList = null;
    private AbonneDAO abonneDAO = null;
    private EmpruntDAO empruntDAO=null;

    public Controleur( String name, List<Trottinette> trottinetteList, AbonneDAO abonneDAO, EmpruntDAO empruntDAO ) {
        this.name = name;
        this.trottinetteList = trottinetteList;
        this.abonneDAO = abonneDAO;
        this.empruntDAO = empruntDAO;
    }

    @Override
    public void start() {
        System.out.println("Composant " + this.getClass().getTypeName() + " demarre. Objet d'acces aux donnees : "
            + abonneDAO.toString());
    }

    @Override
    public void stop() {
        // TODO : write code
    }

    private Response add(Map<String, Object> params) {
        LOG.info("Add method called (" + (params != null?params.toString():"void") + ")");

        // add abonnee
        if(params.containsKey(ABONNE))
            try {
                this.abonneDAO.save((Abonne) params.get(ABONNE));
                return Response.create(Response.OK, "Abonnee added successfuly");
            } catch (IOException e) {
                LOG.error(e.getMessage());
                return Response.create(Response.ERROR, "Abonnee NOT added");
            }

        // add emprunt
        if (params.containsKey(EMPRUNT)) {
            this.empruntDAO.save((Emprunt) params.get(EMPRUNT));
            return Response.create(Response.OK, "Emprunt added successfuly");
        }

        return Response.create(Response.ERROR, "Emprunt NOT added");
    }

    private Response get(Map<String, Object> params) {
        LOG.info("Add method called (" + (params != null?params.toString():"void") + ")");

        // TODO : add trottinette getter when merging with walid
        // TODO : add emprunt getter by date when merging with walid

        return Response.create(Response.NOTFOUND, "Method");
    }

    private Response remove(Map<String, Object> params) throws IOException {
        LOG.info("Remove method called (" + (params != null?params.toString():"void") + ")");

        if(params.containsKey(ABONNE))
            try {
                this.abonneDAO.delete((Abonne) params.get(ABONNE));
                return Response.create(Response.OK, "Abonnee Removed successfuly");
            } catch (IOException e) {
                LOG.error(e.getMessage());
                return Response.create(Response.ERROR, "Abonnee NOT Removed");
            }
        return Response.create(Response.NOTFOUND, "parameter not found or unknown");
    }

    public Response process(String cmd, Map<String, Object> params) throws IOException {
        if(cmd.toUpperCase().equals(Controleur.GET)) {
            return get(params);
        }
        if(cmd.toUpperCase().equals(Controleur.ADD)) {
            return add(params);
        }
        if(cmd.toUpperCase().equals(Controleur.REMOVE)) {
            return remove(params);
        }
        return Response.create(Response.UNKNOWN_CMD, cmd + " command is UNKNOWN");
    }

}
