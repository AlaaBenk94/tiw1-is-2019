package tiw1.emprunt.serveur;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import tiw1.emprunt.contexte.Annuaire;
import tiw1.emprunt.contexte.AnnuaireImpl;
import tiw1.emprunt.model.Abonne;
import tiw1.emprunt.model.dto.EmpruntDTO;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static tiw1.emprunt.model.dto.Constants.*;
import static tiw1.emprunt.serveur.ServeurImpl.ROOT;

public class ServeurTest {

    private static Annuaire context = null;
    public static final String SERVER = ROOT + Serveur.class.getSimpleName();

    private Map<String, Object> params;
    Abonne ab = new Abonne(19L, "Toto", new Date(), new Date());

    @BeforeClass
    public static void setUp() throws Exception {
        context = (new ServeurImpl()).getAnnuaire();
    }

    @Before
    public void beforeTests() {
        params = new HashMap<>();
    }

    @Test
    public void testAnnuaireCreation() {
        System.out.println("testServeur - Not Null");
        assertNotNull(context);
    }

    @Test
    public void abonnementTest() {
        params.put(ABONNE, ab);
        JSONObject response = new JSONObject((String) ((Serveur) context.lookup(SERVER))
                .processRequest(ABONNE, ADD, params));
        assertEquals("Abonnement Failed", OK, response.get("status"));
    }

    @Test
    public void desabonnementTest() {
        params.put(ABONNE, ab);
        JSONObject response = new JSONObject((String) ((Serveur) context.lookup(SERVER))
                .processRequest(ABONNE, REMOVE, params));
        assertEquals("Desabonnement Failed", OK, response.get("status"));
    }

    @Test
    public void createEmpruntTest() {
        EmpruntDTO emprunt = new EmpruntDTO();
        emprunt.setDate(new Date());
        emprunt.setIdAbonne(19L);
        emprunt.setIdTrottinette(15L);

        params.put(EMPRUNT, emprunt);
        JSONObject response = new JSONObject((String) ((Serveur) context.lookup(SERVER))
                .processRequest(EMPRUNT,ADD, params));
        System.out.println(response);
        assertEquals("Emprunt creation failed", OK, response.get("status"));
    }

    @Test
    public void getEmpruntTest() {
        createEmpruntTest();
        params.put(ID, 1L);
        JSONObject response = new JSONObject((String) (((Serveur) context.lookup(SERVER))
                .processRequest(EMPRUNT, GET, params)));
        assertEquals("Emprunt get Failed", OK, response.get("status"));
        assertEquals("Emprunt ID doesn't match", 1, ((JSONObject) response.get("content")).get("id"));
    }

    @Test
    public void getEmpruntByDateTest() {
        createEmpruntTest();
        params.put(DATE, new Date());
        JSONObject response = new JSONObject((String) (((Serveur) context.lookup(SERVER))
                .processRequest(EMPRUNT, GET, params)));
        assertNotEquals("Emprunt by date failed", 0, ((JSONArray) response.get("content")).length());
    }

    @Test
    public void getTrottinetteTest() {
        params.put(ID, 38L);
        JSONObject response = new JSONObject((String) (((Serveur) context.lookup(SERVER))
                .processRequest(TROTINETTE, GET, params)));
        assertEquals(OK, response.get("status"));
        assertEquals(38, ((JSONObject) response.get("content")).get("id"));
    }

    @Test
    public void getTrottinettesTest() {
        JSONObject response = new JSONObject((String) (((Serveur) context.lookup(SERVER))
                .processRequest(TROTINETTE, GET, params)));
        assertNotEquals(0, ((JSONArray) response.get("content")).length());
    }

    @Test
    public void getDispoTrottinetteTest() {
        params.put(DISPO, "");
        params.put(ID, 38L);
        System.out.println((String) ((Serveur) context.lookup(SERVER))
                .processRequest(TROTINETTE, GET, params));
    }

    @Test
    public void getTrottinettePoolTest() {
        // first request lock the resource
        params.put(ID, 38L);
        JSONObject response = new JSONObject((String) (((Serveur) context.lookup(SERVER))
                .processRequest(TROTINETTE, GET, params)));

        // seconde request doesn't get the resource because it is not available
        response = new JSONObject((String) (((Serveur) context.lookup(SERVER))
                .processRequest(TROTINETTE, GET, params)));
        assertEquals(OK, response.get("status"));
        assertEquals("null", response.get("content").toString());

        // wait until the resource is released
        sleep(4);

        // third request successful because the resource is available again
        response = new JSONObject((String) (((Serveur) context.lookup(SERVER))
                .processRequest(TROTINETTE, GET, params)));
        assertEquals(OK, response.get("status"));
        assertEquals(38, ((JSONObject) response.get("content")).get("id"));
        sleep(4);
    }

    private void sleep(long sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


