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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RebelReportTraitorEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Report a traitor rebel successfully")
    public void whenReportTraitor() throws Exception {
        var rebel = toCreateRebel(mockMvc);

        reportTraitor(rebel.getId());
    }

    @Test
    @DisplayName("Report a traitor once must not make a rebel a traitor")
    public void whenReportTraitorOnce_mustNotMakeTraitor() throws Exception {
        var rebel = toCreateRebel(mockMvc);

        reportTraitor(rebel.getId());

        var request = get(String.format("/v1/rebel/%s", rebel.getId()))
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.traitor").value(false));
    }

    @Test
    @DisplayName("Report a traitor three times must make a rabel a traitor")
    public void whenReportTraitor_threeTimes() throws Exception {
        var rebel = toCreateRebel(mockMvc);

        reportTraitor(rebel.getId());
        reportTraitor(rebel.getId());
        reportTraitor(rebel.getId());

        var request = get(String.format("/v1/rebel/%s", rebel.getId()))
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.traitor").value(true));
    }

    private void reportTraitor(Long id) throws Exception {
        var request = put(String.format("/v1/rebel/%s/traitor", id))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk());
    }

}
