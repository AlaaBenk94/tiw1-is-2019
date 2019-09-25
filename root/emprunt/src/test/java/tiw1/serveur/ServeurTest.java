package tiw1.serveur;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import tiw1.emprunt.model.Abonne;
import tiw1.emprunt.persistence.AbonneDAO;

import java.util.Date;
import java.util.Random;

import static org.junit.Assert.*;

public class ServeurTest {

    private Serveur serveur = null;
    private AbonneDAO abonneDAO = null;

    @Before
    public void setUp() throws Exception {
        this.abonneDAO = new AbonneDAO();
        this.serveur = new Serveur(this.abonneDAO);
    }

    @After
    public void tearDown() throws Exception {
        this.abonneDAO.delete(new Abonne(5l, "abonee", new Date(), null));
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
        System.out.println("testAbonnement");
        Abonne ab = new Abonne(5l, "abonee", new Date(), null);
        int initSize;
        try {
            initSize = abonneDAO.getAll().size();
            this.serveur.abonnement(ab);
            assertEquals("testAbonnement [SUCCEED]",initSize+1, abonneDAO.getAll().size());
        } catch (Exception e) {
            fail("testAbonnement [FAILED] : " + e.getMessage());
        }
    }

    @Test
    public void desabonnement() {
        System.out.println("testDesabonnement");
        Abonne ab = (Abonne) abonneDAO.getAll().get(0);
        int initSize;
        try {
             initSize = abonneDAO.getAll().size();
            serveur.desabonnement(ab);
            assertEquals("testDesabonnement [SUCCEED]", initSize-1, abonneDAO.getAll().size());
        } catch (Exception e) {
            fail("testDesabonnement [FAILED] : " + e.getMessage());
        }

    }
}
