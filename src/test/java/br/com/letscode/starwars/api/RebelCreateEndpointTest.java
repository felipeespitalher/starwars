package br.com.letscode.starwars.api;

import br.com.letscode.starwars.data.dto.RebelDTO;
import br.com.letscode.starwars.data.dto.RebelLocationDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static br.com.letscode.starwars.DataUtils.toRebel;
import static br.com.letscode.starwars.JsonUtils.writeValueAsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RebelCreateEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Save rebel with success")
    public void whenCreate_withSuccess() throws Exception {
        var rebel = toRebel();

        var content = writeValueAsString(rebel);
        var request = post("/v1/rebel")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(rebel.getName()))
                .andExpect(jsonPath("$.birth").value(rebel.getBirth().toString()))
                .andExpect(jsonPath("$.gender").value(rebel.getGender().name()))
                .andExpect(jsonPath("$.traitor").value(false))
                .andExpect(jsonPath("$.location.description").value(rebel.getLocation().getDescription()))
                .andExpect(jsonPath("$.location.longitude").value(rebel.getLocation().getLongitude()))
                .andExpect(jsonPath("$.location.latitude").value(rebel.getLocation().getLatitude()))
                .andExpect(jsonPath("$.inventory[?(@.item == \"GUN\" && @.quantity == \"10\")]").exists())
                .andExpect(jsonPath("$.inventory[?(@.item == \"MUNITION\" && @.quantity == \"10\")]").exists())
                .andExpect(jsonPath("$.inventory[?(@.item == \"WATER\" && @.quantity == \"10\")]").exists())
                .andExpect(jsonPath("$.inventory[?(@.item == \"FOOD\" && @.quantity == \"10\")]").exists());
    }

    @Test
    @DisplayName("Save rebel without required parameters must return 400 with messages for missing fields")
    public void whenCreate_withoutRequiredParameters() throws Exception {
        var rebel = new RebelDTO();

        var content = writeValueAsString(rebel);
        var request = post("/v1/rebel")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.errors[?(@.field == \"name\" && @.message == \"Por favor, informe o nome do rebelde\")]").exists())
                .andExpect(jsonPath("$.errors[?(@.field == \"birth\" && @.message == \"Por favor, informe a data de nascimento do rebelde\")]").exists())
                .andExpect(jsonPath("$.errors[?(@.field == \"gender\" && @.message == \"Por favor, informe o genêro do rebelde\")]").exists())
                .andExpect(jsonPath("$.errors[?(@.field == \"location\" && @.message == \"Por favor, informe a localização do rebelde\")]").exists())
                .andExpect(jsonPath("$.errors[?(@.field == \"inventory\" && @.message == \"Por favor, informe o inventário do rebelde\")]").exists());
    }

    @Test
    @DisplayName("Save rebel without location required parameters and birth equals to today must return 400 with messages for missing fields")
    public void whenCreate_withoutLocationRequiredParameters_andInvalidBirthDate() throws Exception {
        var rebel = toRebel();
        rebel.setBirth(LocalDate.now());
        rebel.setLocation(new RebelLocationDTO());

        var content = writeValueAsString(rebel);
        var request = post("/v1/rebel")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.errors[?(@.field == \"birth\" && @.message == \"Por favor, verifique a data de nascimento do rebelde\")]").exists())
                .andExpect(jsonPath("$.errors[?(@.field == \"location.latitude\" && @.message == \"Por favor, informe a latitude da localização do rebelde\")]").exists())
                .andExpect(jsonPath("$.errors[?(@.field == \"location.longitude\" && @.message == \"Por favor, informe a longitude da localização do rebelde\")]").exists())
                .andExpect(jsonPath("$.errors[?(@.field == \"location.description\" && @.message == \"Por favor, informe a descrição da localização do rebelde\")]").exists());
    }

}