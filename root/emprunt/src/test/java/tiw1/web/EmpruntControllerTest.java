//package tiw1.web;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import tiw1.service.EmpruntService;
//
//import static org.mockito.Mockito.verifyNoMoreInteractions;
//
//@ExtendWith(MockitoExtension.class)
//class EmpruntControllerTest {
//
//    private MockMvc mockMvc;
//
//    private ObjectMapper objectMapper;
//
//    @Mock
//    private EmpruntService empruntService;
//
//    @InjectMocks
//    private EmpruntController empruntController;
//
//    @BeforeEach
//    void setUp() {
//        objectMapper = new ObjectMapper();
//        mockMvc = MockMvcBuilders.standaloneSetup(empruntController)
//                .setControllerAdvice(new ResourceControllerAdvice())
//                .build();
//    }
//
//    @AfterEach
//    void tearDown() {
//        verifyNoMoreInteractions(empruntService);
//    }
//
//    @Test
//    @DisplayName("Nominal Case getEmprunt")
//    void getEmpruntNominalCase() {
//    }
//
//    @Test
//    @DisplayName("Not Found Exception getEmprunt")
//    void getEmpruntNotFound() {
//    }
//
//    @Test
//    void saveEmprunt() {
//    }
//
//    @Test
//    void getEmprunts() {
//    }
//}