package tiw1.emprunt.serveur;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ServeurTest {

    public static Serveur serveur = null;

    @BeforeClass
    public static void setUp() throws Exception {
        serveur = new ServeurImpl();
    }

    @Test
    public void testServeurCreation() {
        System.out.println("testServeur - Not Null");
        assertNotNull(serveur);
    }

    @Test
    public void precessRequestTest(){

    }

    @Test
    public void abonnementTest() {
        // TODO : write test
    }

    @Test
    public void desabonnementTest() {
        // TODO : write test
    }

    @Test
    public void createEmpruntTest() {
        // TODO : write test
    }

    @Test
    public void getEmpruntTest() {
        // TODO : write test
    }

    @Test
    public void getDispoTrottinetteTest() {
        // TODO : write test
    }

    @Test
    public void Test() {
        // TODO : write test
    }
}


