package tiw1.serveur;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import tiw1.emprunt.model.Abonne;
import tiw1.emprunt.model.dto.EmpruntDTO;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ServeurTest {

    public static Serveur serveur = null;
    private Map<String, Object> params;
    Abonne ab = new Abonne(19L, "Toto", new Date(), new Date());

    @BeforeClass
    public static void setUp() throws Exception {
        serveur = new ServeurImpl();
    }

    @Before
    public void beforeTests() {
        params = new HashMap<>();
    }

    @Test
    public void testServeurCreation() {
        System.out.println("testServeur - Not Null");
        assertNotNull(serveur);
    }

    @Test
    public void processRequest_ERROR_Test(){
        params.put("ABONNE", new Abonne(19L, "Toto", new Date(), new Date()));
        assertTrue(serveur.processRequest("ADD", params).isOK());
    }

    @Test
    public void abonnementTest() {
        params.put("ABONNE", ab);
        assertTrue(serveur.processRequest("ADD", params).isOK());
    }

    @Test
    public void desabonnementTest() {
        params.put("ABONNE", ab);
        assertTrue(serveur.processRequest("REMOVE", params).isOK());
    }

    @Test
    public void createEmpruntTest() {
        EmpruntDTO emprunt = new EmpruntDTO();
        emprunt.setDate(new Date());
        emprunt.setIdAbonne(19L);
        emprunt.setIdTrottinette(15L);

        params.put("EMPRUNT", emprunt);
        assertTrue(serveur.processRequest("ADD", params).isOK());
    }

    @Test
    public void getEmpruntTest() {
        EmpruntDTO emprunt = new EmpruntDTO();
        emprunt.setDate(new Date());
        emprunt.setIdAbonne(19L);
        emprunt.setIdTrottinette(15L);

        params.put("EMPRUNT", emprunt);
        assertTrue(serveur.processRequest("ADD", params).isOK());

        params.put("DATE", new Date());
        assertEquals(1, ((List) serveur.processRequest("GET", params).getContent()).size());
    }

    @Test
    public void getDispoTrottinetteTest() {
        // TODO : Later
    }
}


