package tiw1.emprunt.serveur;

import org.junit.Before;
import org.junit.BeforeClass;
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
    public void processRequest_ERROR_Test(){
        params.put("ABONNE", new Abonne(19L, "Toto", new Date(), new Date()));
        assertTrue(((Serveur) context.lookup(SERVER)).processRequest("ABONNE", "ADD", params).isOK());
    }

    @Test
    public void abonnementTest() {
        params.put("ABONNE", ab);
        assertTrue(((Serveur) context.lookup(SERVER)).processRequest("ABONNE", "ADD", params).isOK());
    }

    @Test
    public void desabonnementTest() {
        params.put("ABONNE", ab);
        assertTrue(((Serveur) context.lookup(SERVER)).processRequest("ABONNE","REMOVE", params).isOK());
    }

    @Test
    public void createEmpruntTest() {
        EmpruntDTO emprunt = new EmpruntDTO();
        emprunt.setDate(new Date());
        emprunt.setIdAbonne(19L);
        emprunt.setIdTrottinette(15L);

        params.put("EMPRUNT", emprunt);
        assertTrue(((Serveur) context.lookup(SERVER)).processRequest("EMPRUNT","ADD", params).isOK());
    }

    @Test
    public void getEmpruntTest() {
        EmpruntDTO emprunt = new EmpruntDTO();
        emprunt.setDate(new Date());
        emprunt.setIdAbonne(19L);
        emprunt.setIdTrottinette(15L);

        params.put("EMPRUNT", emprunt);
        assertTrue(((Serveur) context.lookup(SERVER)).processRequest("EMPRUNT", "ADD", params).isOK());

        params.put("DATE", new Date());
        assertNotEquals(0, ((List) ((Serveur) context.lookup(SERVER)).processRequest("EMPRUNT", "GET", params).getContent()).size());
    }

    @Test
    public void getDispoTrottinetteTest() {
        // TODO : Later
    }

}


