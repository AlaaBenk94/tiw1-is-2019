package tiw1.maintenance.metier;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import tiw1.maintenance.models.Trottinette;
import tiw1.maintenance.spring.AppConfig;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
public class MaintenanceTest {

    @Autowired
    private Maintenance m;

    @Test
    public void checkContextSetup() {
        assertNotNull(m);
    }

    @Test
    public void testCreerSupprimerTrottinette() {
        Trottinette t = m.creerTrottinette();
        long id = t.getId();
        Trottinette t2 = m.getTrottinetteAndInterventions(id);
        assertEquals(id, t2.getId());
        m.supprimerTrottinette(id);
        assertNull(m.getTrottinetteAndInterventions(id));
    }

    @Test
    public void testUpdateTrottinette() {
        Trottinette t = m.creerTrottinette();
        long id = t.getId();
        boolean t_disp = t.isDisponible();
        Trottinette t2 = new Trottinette(id);
        t2.setDisponible(!t.isDisponible());
        Trottinette t3 = m.updateTrottinette(t2);
        assertNotEquals(t_disp, t3.isDisponible());
    }
}