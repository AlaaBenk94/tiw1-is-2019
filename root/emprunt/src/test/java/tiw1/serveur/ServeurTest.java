package tiw1.serveur;

import org.junit.Before;
import org.junit.Test;
import tiw1.emprunt.model.Abonne;
import tiw1.emprunt.persistence.AbonneDAO;

import java.util.Date;
import java.util.Random;

import static org.junit.Assert.*;

public class ServeurTest {

    private Serveur serveur = null;
    private AbonneDAO abonneDAO = null;
    private Random rand = null;
    @Before
    public void setUp() throws Exception {
        this.abonneDAO = new AbonneDAO();
        this.serveur = new Serveur(this.abonneDAO);
        this.rand = new Random();
    }

    @Test
    public void testAbonneDaoNotNull() {
        System.out.println("testAbonneDao - Not Null");
        assertNotNull(abonneDAO);
    }

    @Test
    public void testServeurCreation() {
        System.out.println("testServeur - Not Null");
        assertNotNull(serveur);
    }

    @Test
    public void testAbonnement() {
        System.out.println("testAbonnement - Right");
        long ID = rand.nextLong();
        Abonne ab = new Abonne(ID, "abonee", new Date(), null);

        try {
            assertEquals(true, abonneDAO.get(ID).isEmpty());
            this.serveur.abonnement(ab);
            assertEquals("testAbonnement - Right [SUCCEED]",true, abonneDAO.get(ID).isPresent());
        } catch (Exception e) {
            fail("testAbonnement - Right [FAILED] : " + e.getMessage());
        }
    }

    @Test
    public void desabonnement() {
        System.out.println("testDesabonnement - Right");
        long ID = rand.nextLong();
        Abonne ab = new Abonne(ID, "abonee", new Date(), null);
        try {
            abonneDAO.save(ab);
            assertEquals(true, abonneDAO.get(ID).isPresent());
            serveur.desabonnement(ab);
            assertEquals("testDesabonnement - Right [SUCCEED]", true, abonneDAO.get(ID).isEmpty());
        } catch (Exception e) {
            fail("testDesabonnement - Right [FAILED] : " + e.getMessage());
        }

    }
}
