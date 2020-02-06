//package tiw1.service;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import tiw1.domain.Trottinette;
//import tiw1.dto.TrottinetteDto;
//import tiw1.exception.ResourceNotFoundException;
//import tiw1.utils.TestUtils;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.doReturn;
//import static org.mockito.Mockito.verify;
//
//@ExtendWith(MockitoExtension.class)
//class TrottinetteServiceTest {
//
//    @Mock
//    private TrottinetteLoader trottinetteLoader;
//
//    @InjectMocks
//    private TrottinetteService trottinetteService;
//
//    @AfterEach
//    public void tearDown() {
//        verifyNoMoreInteractions(trottinetteLoader);
//    }
//
//    @Test
//    @DisplayName("Nominal Case for getTrottinette")
//    void getTrottinetteNominalCase() {
//        Trottinette expectedTrottinette = new Trottinette(TestUtils.TROTTINETTE_ID, true);
//        doReturn(expectedTrottinette)
//                .when(trottinetteLoader).getTrottinette(TestUtils.TROTTINETTE_ID);
//
//        TrottinetteDto actualTrottinette = trottinetteService.getTrottinette(TestUtils.TROTTINETTE_ID);
//
//        assertNotNull(actualTrottinette);
//        TestUtils.assertEqualsTrottinetteResult(expectedTrottinette, actualTrottinette);
//        verify(trottinetteLoader).getTrottinette(TestUtils.TROTTINETTE_ID);
//    }
//
//    @Test
//    @DisplayName("Not Found Exception for getTrottinette")
//    void getTrottinetteNotFound() {
//        doReturn(null)
//                .when(trottinetteLoader).getTrottinette(TestUtils.TROTTINETTE_ID);
//
//        assertThrows(ResourceNotFoundException.class, () -> trottinetteService.getTrottinette(TestUtils.TROTTINETTE_ID));
//
//        verify(trottinetteLoader).getTrottinette(TestUtils.TROTTINETTE_ID);
//    }
//
//    @Test
//    @DisplayName("Nominal Case for getTrottinettes")
//    void getTrottinettesNominalCase() {
//        Map<Long, Trottinette> expectedList = Map.of(TestUtils.TROTTINETTE_ID, new Trottinette(TestUtils.TROTTINETTE_ID, true));
//        doReturn(expectedList)
//                .when(trottinetteLoader).getTrottinettes();
//
//        List<TrottinetteDto> actualList = trottinetteService.getTrottinettes();
//
//        assertEquals(expectedList.size(), actualList.size());
//        TestUtils.assertEqualsTrottinetteResult(expectedList.get(TestUtils.TROTTINETTE_ID), actualList.get(0));
//        verify(trottinetteLoader).getTrottinettes();
//    }
//
//
//    @Test
//    @DisplayName("Nominal Case for getTrottinettes")
//    void getTrottinettesEmptyList() {
//        doReturn(Collections.emptyMap())
//                .when(trottinetteLoader).getTrottinettes();
//
//        List<TrottinetteDto> actualList = trottinetteService.getTrottinettes();
//
//        assertTrue(actualList.isEmpty());
//        verify(trottinetteLoader).getTrottinettes();
//    }
//
//}