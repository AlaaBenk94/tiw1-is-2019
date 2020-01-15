package tiw1.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tiw1.dto.AbonneDto;
import tiw1.exception.ResourceNotFoundException;
import tiw1.service.AbonneService;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tiw1.utils.TestUtils.ABONNE_ID;
import static tiw1.utils.TestUtils.ABONNE_NAME;

@ExtendWith(MockitoExtension.class)
class AbonneControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Mock
    private AbonneService abonneService;

    @InjectMocks
    private AbonneController abonneController;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(abonneController)
                .setControllerAdvice(new ResourceControllerAdvice())
                .build();
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(abonneService);
    }

    @Test
    @DisplayName("Nominal Case getAbonneById")
    void getAbonneByIdNominalCase() throws Exception {
        AbonneDto expectedAbonne = AbonneDto.builder()
                .withId(ABONNE_ID)
                .withName(ABONNE_NAME)
                .withDateDebut(new Date())
                .withDateFin(new Date())
                .build();
        doReturn(expectedAbonne)
                .when(abonneService).getAbonneById(ABONNE_ID, true, "user");

        String actualResponse = mockMvc.perform(get("/abonnes/" + ABONNE_ID).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        AbonneDto actualAbonne = objectMapper.readValue(actualResponse, AbonneDto.class);
        assertTrue(EqualsBuilder.reflectionEquals(expectedAbonne, actualAbonne));
        verify(abonneService).getAbonneById(ABONNE_ID, true, "user");
    }

    @Test
    @DisplayName("Not Found Exception getAbonneById")
    void getAbonneByIdNotFound() throws Exception {
        doThrow(ResourceNotFoundException.class)
                .when(abonneService).getAbonneById(ABONNE_ID, true, "user");

        mockMvc.perform(get("/abonnes/" + ABONNE_ID).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(abonneService).getAbonneById(ABONNE_ID, true, "user");
    }

    @Test
    void saveAbonne() throws Exception {
        ArgumentCaptor<AbonneDto> acAbonne = ArgumentCaptor.forClass(AbonneDto.class);
        AbonneDto expectedAbonne = AbonneDto.builder()
                .withId(ABONNE_ID)
                .withName(ABONNE_NAME)
                .withDateDebut(new Date())
                .withDateFin(new Date())
                .build();
        doReturn(expectedAbonne)
                .when(abonneService).subscribe(any(AbonneDto.class), "name");

        mockMvc.perform(
                post("/abonnes/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expectedAbonne)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedAbonne)));

        verify(abonneService).subscribe(acAbonne.capture(), "name");
        assertTrue(EqualsBuilder.reflectionEquals(expectedAbonne, acAbonne.getValue()));
    }

    @Test
    @DisplayName("Nominal Case for getAbonnes")
    void getAbonnesNominalCase() throws Exception {
        List<AbonneDto> expectedList = List.of(
                AbonneDto.builder()
                        .withId(ABONNE_ID)
                        .withName(ABONNE_NAME)
                        .withDateDebut(new Date())
                        .withDateFin(new Date())
                        .build());
        doReturn(expectedList)
                .when(abonneService).getAbonneList();

        mockMvc.perform(get("/abonnes").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedList), true));

        verify(abonneService).getAbonneList();
    }

    @Test
    @DisplayName("Empty List for getAbonnes")
    void getAbonnesEmptyList() throws Exception {
        doReturn(Collections.emptyList())
                .when(abonneService).getAbonneList();

        mockMvc.perform(get("/abonnes").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.emptyList()), true));

        verify(abonneService).getAbonneList();
    }
}