package br.com.letscode.starwars.api;

import br.com.letscode.starwars.data.dto.RebelLocationDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static br.com.letscode.starwars.DataUtils.toCreateRebel;
import static br.com.letscode.starwars.DataUtils.toLocation;
import static br.com.letscode.starwars.JsonUtils.writeValueAsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RebelUpdateLocationEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Update location with success")
    public void whenUpdateLocation_withSuccess() throws Exception {
        var rebel = toCreateRebel(mockMvc);

        var location = rebel.getLocation();
        location.setDescription("New Location");
        location.setLatitude(BigDecimal.ONE);
        location.setLongitude(BigDecimal.ONE);

        var content = writeValueAsString(location);
        var request = patch(String.format("/v1/rebel/%s/location", rebel.getId()))
                .content(content)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.location.description").value(location.getDescription()))
                .andExpect(jsonPath("$.location.longitude").value(location.getLongitude()))
                .andExpect(jsonPath("$.location.latitude").value(location.getLatitude()));
    }

    @Test
    @DisplayName("Update location without required parameters")
    public void whenUpdateLocation_withoutRequiredParameters() throws Exception {
        var rebel = toCreateRebel(mockMvc);

        var location = new RebelLocationDTO();

        var content = writeValueAsString(location);
        var request = patch(String.format("/v1/rebel/%s/location", rebel.getId()))
                .content(content)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.errors[?(@.field == \"latitude\" && @.message == \"Por favor, informe a latitude da localização do rebelde\")]").exists())
                .andExpect(jsonPath("$.errors[?(@.field == \"longitude\" && @.message == \"Por favor, informe a longitude da localização do rebelde\")]").exists())
                .andExpect(jsonPath("$.errors[?(@.field == \"description\" && @.message == \"Por favor, informe a descrição da localização do rebelde\")]").exists());
    }

    @Test
    @DisplayName("Update Location with empty database")
    public void whenUploadLocation_withEmptyDatabase() throws Exception {
        var location = toLocation();


        var content = writeValueAsString(location);
        var request = patch("/v1/rebel/123/location")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }

}
