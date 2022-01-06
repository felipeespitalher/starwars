package br.com.letscode.starwars.api;

import br.com.letscode.starwars.JsonUtils;
import br.com.letscode.starwars.data.dto.RebelDetailDTO;
import br.com.letscode.starwars.data.dto.TradeDTO;
import br.com.letscode.starwars.data.dto.TradeRebelDTO;
import br.com.letscode.starwars.data.enumeration.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static br.com.letscode.starwars.DataUtils.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TradeEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Trade successfully")
    public void whenTrade_successfully() throws Exception {
        var firstRebel = toRebel();
        firstRebel.getInventory().clear();
        firstRebel.getInventory().add(toRebelItem(Item.GUN, 1));
        firstRebel.getInventory().add(toRebelItem(Item.WATER, 1));

        var secondRebel = toRebel();
        secondRebel.getInventory().clear();
        secondRebel.getInventory().add(toRebelItem(Item.FOOD, 6));

        var firstTrader = toCreateRebel(mockMvc, firstRebel);
        var secondTrader = toCreateRebel(mockMvc, secondRebel);

        var tradeDTO = new TradeDTO();
        tradeDTO.setFirstRebel(toTrader(firstTrader));
        tradeDTO.setSecondRebel(toTrader(secondTrader));

        var content = JsonUtils.writeValueAsString(tradeDTO);
        var request = post("/v1/trade")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isAccepted());

        var postRequest = get("/v1/rebel/" + firstTrader.getId())
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(postRequest)
                .andExpect(jsonPath("$.inventory[?(@.item == \"FOOD\" && @.quantity == \"6\")]").exists());

        postRequest = get("/v1/rebel/" + secondTrader.getId())
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(postRequest)
                .andExpect(jsonPath("$.inventory[?(@.item == \"GUN\" && @.quantity == \"1\")]").exists())
                .andExpect(jsonPath("$.inventory[?(@.item == \"WATER\" && @.quantity == \"1\")]").exists());
    }

    @Test
    @DisplayName("Not found Rebel")
    public void whenTrade_withNotFoundRebel() throws Exception {
        var trader = toCreateRebel(mockMvc);


        var tradeDTO = new TradeDTO();
        tradeDTO.setFirstRebel(toTrader(trader));
        trader.setId(100L);
        tradeDTO.setSecondRebel(toTrader(trader));

        var content = JsonUtils.writeValueAsString(tradeDTO);
        var request = post("/v1/trade")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[?(@.field == \"secondRebel\" && @.message == \"Rebelde não encontrado\")]").exists());
    }

    @Test
    @DisplayName("Trying to make a unfair trade")
    public void whenTrade_withNullTraders() throws Exception {
        var tradeDTO = new TradeDTO();

        var content = JsonUtils.writeValueAsString(tradeDTO);
        var request = post("/v1/trade")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[?(@.message == \"Por favor, verifique os dados do rebelde\")]").exists());
    }

    @Test
    @DisplayName("Trying to make a unfair trade")
    public void whenTrade_withUnfairItems() throws Exception {
        var firstTrader = toCreateRebel(mockMvc);
        var secondTrader = toCreateRebel(mockMvc);

        var tradeDTO = new TradeDTO();
        tradeDTO.setFirstRebel(toTrader(firstTrader));
        tradeDTO.getFirstRebel().getItemsToTrade().get(0).setQuantity(9);
        tradeDTO.setSecondRebel(toTrader(secondTrader));

        var content = JsonUtils.writeValueAsString(tradeDTO);
        var request = post("/v1/trade")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[?(@.message == \"Essa troca não aparenta ser justa\")]").exists());
    }

    @Test
    @DisplayName("Rebel trying to trade items that it doesn't have")
    public void whenTrade_withItemsDoesntHave() throws Exception {
        var firstTrader = toCreateRebel(mockMvc);

        var rebel = toRebel();
        rebel.getInventory().removeIf(item -> item.getItem().equals(Item.GUN));
        var secondTrader = toCreateRebel(mockMvc, rebel);

        var tradeDTO = new TradeDTO();
        tradeDTO.setFirstRebel(toTrader(firstTrader));
        tradeDTO.getFirstRebel().getItemsToTrade().add(toRebelItem(Item.FOOD, 100));
        tradeDTO.setSecondRebel(toTrader(secondTrader));
        tradeDTO.getSecondRebel().getItemsToTrade().add(toRebelItem(Item.GUN, 10));

        var content = JsonUtils.writeValueAsString(tradeDTO);
        var request = post("/v1/trade")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[?(@.field == \"firstRebel\" && @.message == \"O rebelde não possui 100 do item FOOD\")]").exists())
                .andExpect(jsonPath("$.errors[?(@.field == \"secondRebel\" && @.message == \"O rebelde não possui 10 do item GUN\")]").exists());
    }

    @Test
    @DisplayName("Traitors are not allowed to trade")
    public void whenTrade_withTraitor() throws Exception {
        var traitor = toCreateRebel(mockMvc);
        reportTraitor(traitor.getId());
        reportTraitor(traitor.getId());
        reportTraitor(traitor.getId());

        var secondRebel = toCreateRebel(mockMvc);

        var tradeDTO = new TradeDTO();
        tradeDTO.setFirstRebel(toTrader(traitor));
        tradeDTO.setSecondRebel(toTrader(secondRebel));

        var content = JsonUtils.writeValueAsString(tradeDTO);
        var request = post("/v1/trade")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[?(@.field == \"firstRebel\" && @.message == \"Traidores não tem permissão para negociar itens\")]").exists())
                .andExpect(jsonPath("$.errors[?(@.field == \"firstRebel\" && @.message == \"Por favor, verifique os dados do rebelde\")]").exists());
    }

    private TradeRebelDTO toTrader(RebelDetailDTO rebel) {
        var trader = new TradeRebelDTO();
        trader.setId(rebel.getId());
        trader.setItemsToTrade(rebel.getInventory());
        return trader;
    }

    private void reportTraitor(Long id) throws Exception {
        var request = put(String.format("/v1/rebel/%s/traitor", id))
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isOk());
    }


}
