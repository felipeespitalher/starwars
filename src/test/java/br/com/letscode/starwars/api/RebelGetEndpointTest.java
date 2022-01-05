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
public class RebelGetEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Get rebel by id")
    public void whenGet() throws Exception {
        var rebel = toCreateRebel(mockMvc);

        var request = get("/v1/rebel/" + rebel.getId())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(rebel.getName()))
                .andExpect(jsonPath("$.birth").value(rebel.getBirth().toString()))
                .andExpect(jsonPath("$.gender").value(rebel.getGender().name()))
                .andExpect(jsonPath("$.traitor").value(false))
                .andExpect(jsonPath("$.inventory.GUN").value(10))
                .andExpect(jsonPath("$.inventory.MUNITION").value(10))
                .andExpect(jsonPath("$.inventory.WATER").value(10))
                .andExpect(jsonPath("$.inventory.FOOD").value(10))
                .andExpect(jsonPath("$.location.description").value(rebel.getLocation().getDescription()))
                .andExpect(jsonPath("$.location.longitude").value("10.0"))
                .andExpect(jsonPath("$.location.latitude").value("0.0"));
    }

    @Test
    @DisplayName("Get rebel by id with empty database")
    public void whenGet_withEmptyDatabase() throws Exception {
        var request = get("/v1/rebel/123")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }

}
