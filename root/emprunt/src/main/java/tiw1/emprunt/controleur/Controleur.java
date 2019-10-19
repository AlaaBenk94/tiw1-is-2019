package tiw1.emprunt.controleur;

import org.picocontainer.Startable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tiw1.emprunt.interceptor.InterceptorChain;
import tiw1.emprunt.interceptor.JSONConverterInterceptor;
import tiw1.emprunt.interceptor.LoggerInterceptor;
import tiw1.emprunt.model.dto.Response;

import java.io.IOException;
import java.util.Map;

import static tiw1.emprunt.model.dto.Constants.ERROR;
import static tiw1.emprunt.model.dto.Constants.UNKNOWN_COMMAND;

public class Controleur implements Startable {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private static final String ABONNE = "ABONNE";
    private static final String TROTINETTE = "TROTINETTE";
    private static final String EMPRUNT = "EMPRUNT";

    private String name = "";
    private  AbonneResource abonneResource;
    private EmpruntResource empruntResource;
    private TrottinetteResource trottinetteResource;
    private InterceptorChain interceptor;

    public Controleur(String name, AbonneResource abonneResource,
                      EmpruntResource empruntResource, TrottinetteResource trottinetteResource,
                      InterceptorChain interceptor) {
        this.name = name;
        this.abonneResource = abonneResource;
        this.empruntResource = empruntResource;
        this.trottinetteResource = trottinetteResource;
        // TODO : change this line
        this.interceptor = interceptor
                .addInterceptor(new LoggerInterceptor())
                .addInterceptor(new JSONConverterInterceptor());
    }

    @Override
    public void start() {
        LOG.info("Composant " + this.getClass().getSimpleName() + " demarre. Objet d'acces aux donnees : "
                + abonneResource.toString()
                + " | "
                + empruntResource.toString()
                + " | "
                + trottinetteResource.toString());
    }

    @Override
    public void stop() {
        LOG.info("Composant " + this.getClass().getSimpleName() + " Arrete.");
    }

    /**
     * Permet de transferer la requete au bon controleur de ressource.
     * @param commande nom de la ressource
     * @param method la methode a appeler dans la ressource
     * @param params les parametres de la methode
     * @return un objet reponse qui encapsule le contenue
     */
    public Object forwardRequest(String commande, String method, Map<String, Object> params) {
        try {
            if (commande.toUpperCase() == TROTINETTE) {

                return interceptor.setTarget(trottinetteResource)
                        .execute(method, params);
            }
            if(commande.toUpperCase() == ABONNE) {
                return interceptor.setTarget(abonneResource)
                        .execute(method, params);
            }
            if(commande.toUpperCase() == EMPRUNT) {
                return interceptor.setTarget(empruntResource)
                        .execute(method, params);
            }

            return Response.create(UNKNOWN_COMMAND, "Unknown resource name");

        }catch (IOException e) {
            e.printStackTrace();
        }
        return Response.create(ERROR, "There was an error when precessing the request");
    }

}
