package tiw1.service;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tiw1.domain.Emprunt;
import tiw1.dto.EmpruntDto;
import tiw1.exception.ResourceNotFoundException;
import tiw1.repository.EmpruntRepository;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static tiw1.utils.TestUtils.ABONNE_ID;
import static tiw1.utils.TestUtils.EMPRUNT_ID;
import static tiw1.utils.TestUtils.TROTTINETTE_ID;
import static tiw1.utils.TestUtils.assertEqualsEmpruntResult;

@ExtendWith(MockitoExtension.class)
class EmpruntServiceTest {

    @Mock
    private EmpruntRepository empruntRepository;

    @InjectMocks
    private EmpruntService empruntService;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(empruntRepository);
    }

    @Test
    @DisplayName("Nominal Case for getEmprunt")
    void getEmpruntNominalCase() {
        Emprunt expectedEmprunt = new Emprunt(EMPRUNT_ID, new Date(), ABONNE_ID, TROTTINETTE_ID);
        doReturn(Optional.of(expectedEmprunt))
                .when(empruntRepository).findById(EMPRUNT_ID);

        EmpruntDto actualEmprunt = empruntService.getEmprunt(EMPRUNT_ID, true, "user");

        assertNotNull(actualEmprunt);
        assertEqualsEmpruntResult(expectedEmprunt, actualEmprunt);
        verify(empruntRepository).findById(EMPRUNT_ID);
    }

    @Test
    @DisplayName("NotFoundException for getEmprunt")
    void getEmpruntNotFound() {
        doReturn(Optional.empty())
                .when(empruntRepository).findById(EMPRUNT_ID);

        assertThrows(ResourceNotFoundException.class, () -> empruntService.getEmprunt(EMPRUNT_ID, true, "user"));

        verify(empruntRepository).findById(EMPRUNT_ID);
    }

    @Test
    void saveEmprunt() {
        ArgumentCaptor<Emprunt> acEmprunt = ArgumentCaptor.forClass(Emprunt.class);
        Emprunt expectedEmprunt = new Emprunt(EMPRUNT_ID, new Date(), ABONNE_ID, TROTTINETTE_ID);
        EmpruntDto inputEmpruntDto = EmpruntDto.builder()
                .withId(expectedEmprunt.getId())
                .withDate(expectedEmprunt.getDate())
                .withIdAbonne(expectedEmprunt.getIdAbonne())
                .withIdTrottinette(expectedEmprunt.getIdTrottinette())
                .build();

        doReturn(expectedEmprunt)
                .when(empruntRepository).save(any(Emprunt.class));

        EmpruntDto actualEmprunt = empruntService.saveEmprunt(inputEmpruntDto, "name");

        verify(empruntRepository).save(acEmprunt.capture());
        assertTrue(EqualsBuilder.reflectionEquals(expectedEmprunt, acEmprunt.getValue()));
        assertEqualsEmpruntResult(expectedEmprunt, actualEmprunt);
    }

}