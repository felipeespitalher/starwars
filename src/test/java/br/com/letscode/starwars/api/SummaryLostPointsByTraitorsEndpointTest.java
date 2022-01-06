package br.com.letscode.starwars.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static br.com.letscode.starwars.DataUtils.toCreateRebel;
import static br.com.letscode.starwars.DataUtils.toCreateTraitorRebel;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SummaryLostPointsByTraitorsEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Check points lost by traitors")
    public void checkPointsLostByTraitors() throws Exception {
        toCreateRebel(mockMvc);
        toCreateRebel(mockMvc);
        toCreateRebel(mockMvc);
        toCreateTraitorRebel(mockMvc);

        var request = get("/v1/summary/lostPointsByTraitors")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.points").value(100));
    }

}
