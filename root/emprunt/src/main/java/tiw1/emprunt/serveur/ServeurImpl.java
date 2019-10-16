package tiw1.emprunt.serveur;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.behaviors.Caching;
import org.picocontainer.parameters.ConstantParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tiw1.emprunt.contexte.Annuaire;
import tiw1.emprunt.contexte.AnnuaireImpl;
import tiw1.emprunt.contexte.Observable;
import tiw1.emprunt.controleur.AbonneResource;
import tiw1.emprunt.controleur.Controleur;
import tiw1.emprunt.controleur.EmpruntResource;
import tiw1.emprunt.controleur.TrottinetteResource;
import tiw1.emprunt.model.Trottinette;
import tiw1.emprunt.model.dto.Response;
import tiw1.emprunt.persistence.AbonneDAO;
import tiw1.emprunt.persistence.EmpruntDAO;
import tiw1.emprunt.persistence.TrottinetteLoader;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static org.picocontainer.Characteristics.NO_CACHE;
import static org.picocontainer.Characteristics.SDI;

public class ServeurImpl implements Serveur {
    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());

    public static final String ROOT = "/";
    public static final String APPLICATION = "/serveur/application/";
    public static final String METIER = "/serveur/application/metier/";
    public static final String PERSISTENCE = "/serveur/application/persistence/";

    private  Controleur contoleurMaster;
    private  Annuaire annuaire;
    private MutablePicoContainer myContainer;

    private JSONObject CONFIG;

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("test-pu");


    public ServeurImpl() {
        // Load Config from file
        loadConfig();

        // Container setup
        myContainer = initContainer();

        // initialize Annuaire
        initAnnuaire();

        // Getting instance
        contoleurMaster = myContainer.getComponent(Controleur.class);

        // Starting instances
        myContainer.start();

    }

    private void loadConfig() {
        try {

            InputStream is = this.getClass().getClassLoader().getResourceAsStream( "web.json");
            String jsonTxt = IOUtils.toString(is);
            CONFIG = (new JSONObject(jsonTxt)).getJSONObject("application-config");

        } catch (IOException e) {
            e.printStackTrace();
            LOG.info("There was an error while loading config file");
        }

        LOG.info("Config file loaded");
    }

    private MutablePicoContainer initContainer() {
        return (new DefaultPicoContainer(new Caching())
                .addComponent(AbonneResource.class)
                .addComponent(EmpruntResource.class)
                .addComponent(TrottinetteResource.class)
                .addComponent(Controleur.class)
                .addComponent(String.class)
                .addComponent(EntityManager.class, emf.createEntityManager())
                .addComponent(AbonneDAO.class, AbonneDAO.class, new ConstantParameter("abonnes.json"))
                .addComponent(AnnuaireImpl.class)
                .as(SDI).addComponent(EmpruntDAO.class)
                .as(NO_CACHE).addComponent(Map.class, HashMap.class));
    }


    /**
     * Initialize Annuaire with diffrente Components & resources
     */
    private void initAnnuaire() {
        // Binding registry
        annuaire = myContainer.getComponent(Annuaire.class);
        annuaire.rebind(ROOT + Serveur.class.getSimpleName(), this);

        annuaire.rebind(APPLICATION + AbonneResource.class.getSimpleName(),
                myContainer.getComponent(AbonneResource.class));
        annuaire.rebind(APPLICATION + EmpruntResource.class.getSimpleName(),
                myContainer.getComponent(EmpruntResource.class));
        annuaire.rebind(APPLICATION + TrottinetteResource.class.getSimpleName(),
                myContainer.getComponent(TrottinetteResource.class));

        annuaire.rebind(METIER + Trottinette.class.getSimpleName(),
                loadTrottinette());

        annuaire.rebind(PERSISTENCE + AbonneDAO.class.getSimpleName(),
                myContainer.getComponent(AbonneDAO.class));
        annuaire.rebind(PERSISTENCE + EmpruntDAO.class.getSimpleName(),
                myContainer.getComponent(EmpruntDAO.class));
        annuaire.rebind(APPLICATION + EntityManager.class.getSimpleName(),
                myContainer.getComponent(EntityManager.class));


        annuaire.rebind(EmpruntDAO.class.getSimpleName(), myContainer.getComponent(EmpruntDAO.class));
        annuaire.rebind(Trottinette.class.getSimpleName(), loadTrottinette());

        // Adding Observers
        ((Observable) annuaire).addObserver(myContainer.getComponent(AbonneResource.class));
        ((Observable) annuaire).addObserver(myContainer.getComponent(EmpruntResource.class));
        ((Observable) annuaire).addObserver(myContainer.getComponent(TrottinetteResource.class));
    }


    private Map<Long, Trottinette> loadTrottinette() {
        try {
            TrottinetteLoader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TrottinetteLoader.getTrottinettes();
    }

    @Override
    public Response processRequest(String commande, String method, Map<String, Object> params) {
        return contoleurMaster.forwardRequest(commande, method, params);
    }

    @Override
    public Annuaire getAnnuaire() {
        return this.annuaire;
    }
}
