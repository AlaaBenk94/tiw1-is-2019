package tiw1.emprunt.serveur.controleur;

import org.picocontainer.Startable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tiw1.emprunt.model.Abonne;
import tiw1.emprunt.model.Emprunt;
import tiw1.emprunt.model.Trottinette;
import tiw1.emprunt.model.dto.EmpruntDTO;
import tiw1.emprunt.model.dto.Response;
import tiw1.emprunt.persistence.AbonneDAO;
import tiw1.emprunt.persistence.EmpruntDAO;
import tiw1.emprunt.persistence.TrottinetteLoader;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

public class Controleur implements Startable {
    private static final String ADD = "ADD";
    private static final String REMOVE = "REMOVE";
    private static final String GET = "GET";

    private static final String ABONNE = "ABONNE";
    private static final String ID = "ID";
    private static final String DATE = "DATE";
    private static final String EMPRUNT = "EMPRUNT";

    private final Logger LOG = LoggerFactory.getLogger(Controleur.class);

    private String name = "";
    private Map<Long, Trottinette> trottinetteList;
    private AbonneDAO abonneDAO;
    private EmpruntDAO empruntDAO;

    public Controleur( String name, Map<Long, Trottinette> trottinetteList, AbonneDAO abonneDAO, EmpruntDAO empruntDAO ) {
        this.name = name;
        this.trottinetteList = trottinetteList;
        this.abonneDAO = abonneDAO;
        this.empruntDAO = empruntDAO;
    }

    @Override
    public void start() {
        LOG.info("Composant " + this.getClass().getTypeName() + " demarre. Objet d'acces aux donnees : "
            + abonneDAO.toString() + empruntDAO.toString());

        try {
            TrottinetteLoader.load();
            this.trottinetteList = TrottinetteLoader.getTrottinettes();
        } catch (Exception e) {
            LOG.error("Can't load trottinettes : " + e.getMessage());
        }
    }



    @Override
    public void stop() {
        // TODO : write code
    }

    private Response add(Map<String, Object> params) {
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
            Emprunt emprunt = new Emprunt((EmpruntDTO) params.get(EMPRUNT));
            this.empruntDAO.save(emprunt);
            return Response.create(Response.OK, "Emprunt created Successfully", emprunt);
        }

        return Response.create(Response.ERROR, "Emprunt NOT added");
    }

    private Response get(Map<String, Object> params) {
        try {
            // Check if Trottinette is Available
            if (params.containsKey(ID))
                return Response.create(Response.OK, trottinetteList.get(params.get(ID)).isDisponible() + "");

            // Check emprunt by date
            if (params.containsKey(DATE))
                return Response.create(Response.OK, "", empruntDAO.getByDate((Date) params.get(DATE)).get());
        } catch (ClassCastException e) {
            return Response.create(Response.ERROR, "Invalid argument value");
        }

        return Response.create(Response.NOTFOUND, "Method not found");
    }

    private Response remove(Map<String, Object> params) throws IOException {

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
        if(cmd.toUpperCase().equals(Controleur.GET))
            return get(params);
        if(cmd.toUpperCase().equals(Controleur.ADD))
            return add(params);
        if(cmd.toUpperCase().equals(Controleur.REMOVE))
            return remove(params);
        return Response.create(Response.UNKNOWN_CMD, cmd + " command is UNKNOWN");
    }

}
