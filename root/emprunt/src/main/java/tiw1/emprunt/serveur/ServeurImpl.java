package tiw1.emprunt.serveur;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.behaviors.Caching;
import org.picocontainer.parameters.ConstantParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tiw1.annotation.annotations.NIVEAU;
import tiw1.annotation.annotations.Todo;
import tiw1.emprunt.contexte.Annuaire;
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
import java.util.Map;
import java.util.Properties;

import static org.picocontainer.Characteristics.*;

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
        LOG.info("======================== InitContainer START ========================");
        myContainer = initContainer();
        LOG.info("======================== InitContainer DONE ========================");


        // initialize Annuaire
        LOG.info("======================== InitAnnuaire START ========================");
        initAnnuaire();
        LOG.info("======================== InitAnnuaire DONE ========================");

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

        JSONArray persistence = CONFIG.getJSONArray("persistence-components");

        myContainer = new DefaultPicoContainer(new Caching())
                .addComponent(EntityManager.class, emf.createEntityManager());

        addComponents(myContainer, "business-components");
        addComponents(myContainer, "service-components");
        addComponents(myContainer, "persistence-components");

        return myContainer;

    }

    /**
     * Permet d'ajouter les composants dans le conteneur en fonction le fichier de configuration
     * @param container le conteneur
     * @param components categorie des composants
     */
    private void addComponents(MutablePicoContainer container, String components) {
        JSONArray compList = CONFIG.getJSONArray(components);

        for( Object singleComponent : compList ) {

            Properties  sdiRequired = CDI,
                    cacheRequired = CACHE;
            String      fileRequired = null;

            String className = ((JSONObject) singleComponent).getString("class-name");

            // Get Component Paramerter
            if(((JSONObject) singleComponent).has("params")) {
                JSONArray params = ((JSONObject) singleComponent).getJSONArray("params");
                for (Object singleParam : params) {
                    String name = ((JSONObject) singleParam).getString("name");
                    switch (name) {
                        case "file":
                            fileRequired = ((JSONObject) singleParam).getString("value");
                            break;
                        case "sdi":
                            sdiRequired = ((JSONObject) singleParam).getBoolean("value") ? SDI : CDI;
                            break;
                        case "cache":
                            cacheRequired = ((JSONObject) singleParam).getBoolean("value") ? CACHE : NO_CACHE;
                            break;
                    }
                }
            }

            try {

                System.out.println(className + " :: " + fileRequired + " :: " + sdiRequired + " :: " + cacheRequired);

                if(fileRequired != null)
                    container.as(sdiRequired, cacheRequired).addComponent(Class.forName(className),
                            Class.forName(className), new ConstantParameter(fileRequired));
                else
                    container.as(sdiRequired, cacheRequired).addComponent(Class.forName(className));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
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

    @Todo(value =NIVEAU.BUG,auteur = "AISSBEN",destinataire = "GITLAB", commentaire = "TestComment")
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
