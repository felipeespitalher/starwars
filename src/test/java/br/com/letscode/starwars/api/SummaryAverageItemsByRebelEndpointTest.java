package br.com.letscode.starwars.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static br.com.letscode.starwars.DataUtils.toCreateRebel;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SummaryAverageItemsByRebelEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Check average items per rebel")
    public void checkAverageItemsPerRebel() throws Exception {
        toCreateRebel(mockMvc);
        toCreateRebel(mockMvc);
        toCreateRebel(mockMvc);

        var request = get("/v1/summary/averageItemsByRebel")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[?(@.item == \"FOOD\" && @.quantity == \"10.0\")]").exists())
                .andExpect(jsonPath("$.[?(@.item == \"GUN\" && @.quantity == \"10.0\")]").exists())
                .andExpect(jsonPath("$.[?(@.item == \"MUNITION\" && @.quantity == \"10.0\")]").exists())
                .andExpect(jsonPath("$.[?(@.item == \"WATER\" && @.quantity == \"10.0\")]").exists());
    }

}
