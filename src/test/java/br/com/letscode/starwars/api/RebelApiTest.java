package br.com.letscode.starwars.api;

import br.com.letscode.starwars.JsonUtils;
import br.com.letscode.starwars.data.dto.RebelDTO;
import br.com.letscode.starwars.data.dto.RebelLocationDTO;
import br.com.letscode.starwars.data.enumeration.Gender;
import br.com.letscode.starwars.data.enumeration.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RebelApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Save rebel with success")
    public void whenCreate_withSuccess() throws Exception {
        var location = new RebelLocationDTO();
        location.setDescription("Foobar");
        location.setLatitude(BigDecimal.ZERO);
        location.setLongitude(BigDecimal.TEN);

        var inventory = new HashMap<Item, Integer>();
        inventory.put(Item.GUN, 10);
        inventory.put(Item.MUNITION, 10);
        inventory.put(Item.WATER, 10);
        inventory.put(Item.FOOD, 10);

        var rebel = new RebelDTO();
        rebel.setName("Foobar");
        rebel.setBirth(LocalDate.now().minusYears(32));
        rebel.setGender(Gender.FEMALE);
        rebel.setLocation(location);
        rebel.setInventory(inventory);

        var content = JsonUtils.writeValueAsString(rebel);
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
                .andExpect(jsonPath("$.inventory.GUN").value(10))
                .andExpect(jsonPath("$.inventory.MUNITION").value(10))
                .andExpect(jsonPath("$.inventory.WATER").value(10))
                .andExpect(jsonPath("$.inventory.FOOD").value(10))
                .andExpect(jsonPath("$.location.description").value(location.getDescription()))
                .andExpect(jsonPath("$.location.longitude").value(location.getLongitude()))
                .andExpect(jsonPath("$.location.latitude").value(location.getLatitude()));
    }

    @Test
    @DisplayName("Save rebel without required parameters must return 400 with messages for missing fields")
    public void whenCreate_withoutRequiredParameters() throws Exception {
        var rebel = new RebelDTO();

        var content = JsonUtils.writeValueAsString(rebel);
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
        var rebel = new RebelDTO();
        rebel.setName("Foobar");
        rebel.setBirth(LocalDate.now());
        rebel.setGender(Gender.FEMALE);
        rebel.setLocation(new RebelLocationDTO());
        rebel.setInventory(new HashMap<>());

        var content = JsonUtils.writeValueAsString(rebel);
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

    @Test
    @DisplayName("Get rebel by id")
    public void whenGet() throws Exception {
        var request = get("/v1/rebel/1")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Update location with success")
    public void whenUpdateLocation_withSuccess() throws Exception {
        var location = new RebelLocationDTO();
        location.setDescription("Foobar");
        location.setLatitude(BigDecimal.ZERO);
        location.setLongitude(BigDecimal.TEN);

        var content = JsonUtils.writeValueAsString(location);
        var request = patch("/v1/rebel/1/location")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Update location without required parameters")
    public void whenUpdateLocation_withoutRequiredParameters() throws Exception {
        var location = new RebelLocationDTO();

        var content = JsonUtils.writeValueAsString(location);
        var request = patch("/v1/rebel/1/location")
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
    @DisplayName("Report a traitor rebel successfully")
    public void whenReportTraitor() throws Exception {

        var request = patch("/v1/rebel/1/traitor?traitor=2")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk());
    }

}