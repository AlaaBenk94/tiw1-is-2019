package tiw1.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tiw1.dto.TrottinetteDto;
import tiw1.exception.ResourceNotFoundException;
import tiw1.service.TrottinetteService;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tiw1.utils.TestUtils.TROTTINETTE_ID;

@ExtendWith(MockitoExtension.class)
class TrottinetteControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Mock
    private TrottinetteService trottinetteService;

    @InjectMocks
    private TrottinetteController trottinetteController;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(trottinetteController)
                .setControllerAdvice(new ResourceControllerAdvice())
                .build();
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(trottinetteService);
    }

    @Test
    @DisplayName("Nominal Case getTrottinetteById")
    void getTrottinetteByIdNominalCase() throws Exception {
        TrottinetteDto expectedTrottinetteDto = TrottinetteDto.builder()
                .withId(TROTTINETTE_ID)
                .withDisponible(true)
                .build();
        doReturn(expectedTrottinetteDto)
                .when(trottinetteService).getTrottinette(TROTTINETTE_ID);

        mockMvc.perform(get("/trottinettes/" + TROTTINETTE_ID).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedTrottinetteDto), true));

        verify(trottinetteService).getTrottinette(TROTTINETTE_ID);
    }

    @Test
    @DisplayName("Not Found Exception getTrottinetteById")
    void getTrottinetteByIdNotFound() throws Exception {
        doThrow(ResourceNotFoundException.class)
                .when(trottinetteService).getTrottinette(TROTTINETTE_ID);

        mockMvc.perform(get("/trottinettes/" + TROTTINETTE_ID).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());

        verify(trottinetteService).getTrottinette(TROTTINETTE_ID);
    }

    @Test
    @DisplayName("EmptyList for getTrottinettes")
    void getTrottinettesEmptyList() throws Exception {
        doReturn(Collections.emptyList())
                .when(trottinetteService).getTrottinettes();

        mockMvc.perform(get("/trottinettes").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.emptyList())));

        verify(trottinetteService).getTrottinettes();
    }

    @Test
    @DisplayName("Nominal Case for getTrottinettes")
    void getTrottinettesNominalCase() throws Exception {
        List<TrottinetteDto> expectedTrottinetteList = List.of(
                TrottinetteDto.builder()
                        .withId(TROTTINETTE_ID)
                        .withDisponible(true)
                        .build());
        doReturn(expectedTrottinetteList)
                .when(trottinetteService).getTrottinettes();

        mockMvc.perform(get("/trottinettes").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedTrottinetteList), true))
                .andReturn();

        verify(trottinetteService).getTrottinettes();
    }
}