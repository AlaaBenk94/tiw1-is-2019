package tiw1.emprunt;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import tiw1.emprunt.model.Emprunt;
import tiw1.emprunt.persistence.EmpruntDAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class EmpruntDaoTest {
    private EmpruntDAO dao = new EmpruntDAO();
    private Emprunt emprunt = new Emprunt(1L, new Date(), 1L, 1L);
    private static EntityManagerFactory emf;
    private EntityManager em;

    @BeforeClass
    public static void setupEntityManager() {
        emf = Persistence.createEntityManagerFactory("test-pu");
    }

    @Before
    public void ajoutEmprunt() {
        em = emf.createEntityManager();
        dao.setEm(em);
        dao.save(emprunt);
    }

    @After
    public void supprimeEm() {
        em.close();
        em = null;
    }

    @Test
    public void testEmprunt() {
        assertEquals(emprunt, dao.get(emprunt.getId()).get());
    }

    @Test
    public void testListeEmprunt() {
        assertNotEquals(0, dao.getAll().size());
    }

    @Test
    public void getByDate() {
        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse("27/12/1994");
            Emprunt emp = new Emprunt(2L, date, 2L, 2L);
            dao.save(emp);
            assertEquals(1, dao.getByDate(date).get().size());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
