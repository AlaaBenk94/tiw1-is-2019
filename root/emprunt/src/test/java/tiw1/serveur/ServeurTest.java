package tiw1.serveur;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.picocontainer.*;
import tiw1.emprunt.model.Abonne;
import tiw1.emprunt.persistence.AbonneDAO;
import tiw1.emprunt.persistence.EmpruntDAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class ServeurTest {

    private Serveur serveur = null;
    private AbonneDAO abonneDAO = null;
    private EmpruntDAO empruntDAO = null;
    private EmpruntDAO empruntDAO = null;

    @BeforeClass
    public void setUp() throws Exception {
        // TODO : rewrite this part
        this.abonneDAO = new AbonneDAO();
        this.empruntDAO = new EmpruntDAO();
        this.serveur = new Serveur(abonneDAO, empruntDAO);
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
    public void testEmpruntDAO(){
        System.out.println("testEmpruntDao - Not Null");
        assertNotNull(empruntDAO);
    }

    @Test
    public void testServeurCreation() {
        System.out.println("testServeur - Not Null");
        assertNotNull(serveur);
    }

    @Test
    public void testAbonnement() {
        // TODO : rewrite this test
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
        // TODO : rewrite this test
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
    
    @Test
    public void testLoadTrotinettes(){
        try{

        }
        catch(Exception e){
            //fail
        }
    }

    // TODO : Add Emprunt tests

    // provisoire
    @Test
    public void picoContainerTest() {
        Serveur serv = new Serveur();
        assertNotNull(Serveur.getControleur());
    }

}


