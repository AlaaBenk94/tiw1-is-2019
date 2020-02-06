package tiw1.utils;

import tiw1.domain.Abonne;
import tiw1.domain.Emprunt;
import tiw1.domain.Trottinette;
import tiw1.dto.AbonneDto;
import tiw1.dto.EmpruntDto;
import tiw1.dto.TrottinetteDto;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Contains static methods and constants to use in tests
 */
public class TestUtils {
    public static final Long ABONNE_ID = 5L;
    public static final String ABONNE_NAME = "abonne";
    public static final Long TROTTINETTE_ID = 1L;
    public static final Long EMPRUNT_ID = 3L;

    public static void assertAbonneResult(Abonne expectedAbonne, AbonneDto actualAbonneDto) {
        assertEquals(expectedAbonne.getId(), actualAbonneDto.getId());
        assertEquals(expectedAbonne.getName(), actualAbonneDto.getName());
        assertEquals(expectedAbonne.getDateDebut(), actualAbonneDto.getDateDebut());
        assertEquals(expectedAbonne.getDateFin(), actualAbonneDto.getDateFin());
    }

//    public static void assertAbonneResult(Abonne expectedAbonne, Abonne actualAbonne) {
//        assertEquals(expectedAbonne.getId(), actualAbonne.getId());
//        assertEquals(expectedAbonne.getName(), actualAbonne.getName());
//        assertEquals(expectedAbonne.getDateDebut(), actualAbonne.getDateDebut());
//        assertEquals(expectedAbonne.getDateFin(), actualAbonne.getDateFin());
//    }

    public static void assertEqualsTrottinetteResult(Trottinette expectedTrottinette, TrottinetteDto actualTrottinette) {
        assertEquals(expectedTrottinette.getId(), actualTrottinette.getId());
        assertEquals(expectedTrottinette.isDisponible(), actualTrottinette.isDisponible());
    }

    public static void assertEqualsEmpruntResult(Emprunt expectedEmprunt, EmpruntDto actualEmprunt) {
        assertEquals(expectedEmprunt.getDate(), actualEmprunt.getDate());
        assertEquals(expectedEmprunt.getIdAbonne(), actualEmprunt.getIdAbonne());
        assertEquals(expectedEmprunt.getIdTrottinette(), actualEmprunt.getIdTrottinette());
    }

//    public static void assertEqualsEmpruntResult(Emprunt expectedEmprunt, Emprunt actualEmprunt) {
//        assertEquals(expectedEmprunt.getId(), actualEmprunt.getId());
//        assertEquals(expectedEmprunt.getDate(), actualEmprunt.getDate());
//        assertEquals(expectedEmprunt.getIdAbonne(), actualEmprunt.getIdAbonne());
//        assertEquals(expectedEmprunt.getIdTrottinette(), actualEmprunt.getIdTrottinette());
//    }
}
