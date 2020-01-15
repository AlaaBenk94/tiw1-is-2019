//package tiw1;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//public class Tiw1Tp3ApplicationTests {
//
//    @Autowired
//    private WebApplicationContext wac;
//
//    @Test
//    public void generateSwagger() throws Exception {
//        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
//        mockMvc.perform(MockMvcRequestBuilders.get("/v2/api-docs").accept(MediaType.APPLICATION_JSON)
//                .header("authorization", "Bearer " + "token"))
//                .andDo((result) -> {
//                    try (PrintWriter out = new PrintWriter("src/main/resources/swagger.yaml")) {
//                        out.println(asYaml(result.getResponse().getContentAsString()));
//                    }
//                });
//    }
//
//    private String asYaml(String jsonString) throws IOException {
//        JsonNode jsonNodeTree = new ObjectMapper().readTree(jsonString);
//        return new YAMLMapper().writeValueAsString(jsonNodeTree);
//    }
//
//
//    @Test
//    public void contexLoads() throws Exception {
//        assertThat(wac).isNotNull();
//    }
//}
