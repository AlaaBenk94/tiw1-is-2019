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
import tiw1.domain.Abonne;
import tiw1.dto.AbonneDto;
import tiw1.exception.ResourceNotFoundException;
import tiw1.repository.AbonneRepository;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static tiw1.utils.TestUtils.ABONNE_ID;
import static tiw1.utils.TestUtils.ABONNE_NAME;
import static tiw1.utils.TestUtils.assertAbonneResult;

@ExtendWith(MockitoExtension.class)
class AbonneServiceTest {

    @Mock
    private AbonneRepository abonneRepository;

    @InjectMocks
    private AbonneService abonneService;

    @AfterEach
    public void tearDown() {
        verifyNoMoreInteractions(abonneRepository);
    }

    @Test
    @DisplayName("Nominal Case for getAbonneById")
    void getAbonneByIdNominalCase() {
        Abonne expectedAbonne = new Abonne(ABONNE_ID, ABONNE_NAME, new Date(), new Date());
        doReturn(Optional.of(expectedAbonne))
                .when(abonneRepository).findById(ABONNE_ID);

        AbonneDto actualAbonne = abonneService.getAbonneById(ABONNE_ID, true, "user");

        assertAbonneResult(expectedAbonne, actualAbonne);
        verify(abonneRepository).findById(ABONNE_ID);
    }

    @Test
    @DisplayName("Not Found Exception for getAbonneById")
    void getAbonneByIdNotFound() {
        doReturn(Optional.empty())
                .when(abonneRepository).findById(ABONNE_ID);

        assertThrows(ResourceNotFoundException.class, () -> abonneService.getAbonneById(ABONNE_ID, true, "user"));
        verify(abonneRepository).findById(ABONNE_ID);
    }


    @Test
    @DisplayName("Nominal Case for subscribe")
    void saveAbonneNominalCase() {
        ArgumentCaptor<Abonne> acAbonne = ArgumentCaptor.forClass(Abonne.class);
        Abonne expectedAbonne = new Abonne(ABONNE_ID, ABONNE_NAME, new Date(), new Date());
        AbonneDto inputAbonneDto = AbonneDto.builder()
                .withId(expectedAbonne.getId())
                .withName(expectedAbonne.getName())
                .withDateDebut(expectedAbonne.getDateDebut())
                .withDateFin(expectedAbonne.getDateFin())
                .build();

        doReturn(expectedAbonne)
                .when(abonneRepository).save(any(Abonne.class));

        AbonneDto actualAbonne = abonneService.subscribe(inputAbonneDto, "name");

        verify(abonneRepository).save(acAbonne.capture());
        assertTrue(EqualsBuilder.reflectionEquals(expectedAbonne, acAbonne.getValue()));
        assertAbonneResult(expectedAbonne, actualAbonne);
    }

    @Test
    @DisplayName("Nominal Case for getAbonneList")
    void getAbonneListNominalCase() {
        List<Abonne> expectedList = List.of(new Abonne(ABONNE_ID, ABONNE_NAME, new Date(), new Date()));
        doReturn(expectedList)
                .when(abonneRepository).findAll();

        List<AbonneDto> actualList = abonneService.getAbonneList();

        assertEquals(expectedList.size(), actualList.size());
        assertAbonneResult(expectedList.get(0), actualList.get(0));
        verify(abonneRepository).findAll();
    }

    @Test
    @DisplayName("Empty List for getAbonneList")
    void getAbonneListEmptyList() {
        doReturn(Collections.emptyList())
                .when(abonneRepository).findAll();

        List<AbonneDto> actualList = abonneService.getAbonneList();

        assertTrue(actualList.isEmpty());
        verify(abonneRepository).findAll();
    }

}