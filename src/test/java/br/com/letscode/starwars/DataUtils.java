package br.com.letscode.starwars;

import br.com.letscode.starwars.data.dto.RebelDTO;
import br.com.letscode.starwars.data.dto.RebelDetailDTO;
import br.com.letscode.starwars.data.dto.RebelLocationDTO;
import br.com.letscode.starwars.data.enumeration.Gender;
import br.com.letscode.starwars.data.enumeration.Item;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static br.com.letscode.starwars.JsonUtils.parse;
import static br.com.letscode.starwars.JsonUtils.writeValueAsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class DataUtils {

    public static RebelDTO toRebel() {
        var location = toLocation();
        var inventory = toInventory();
        var rebel = new RebelDTO();
        rebel.setName("Foobar");
        rebel.setBirth(LocalDate.now().minusYears(32));
        rebel.setGender(Gender.FEMALE);
        rebel.setLocation(location);
        rebel.setInventory(inventory);
        return rebel;
    }

    public static RebelLocationDTO toLocation() {
        var location = new RebelLocationDTO();
        location.setDescription("Foobar");
        location.setLatitude(BigDecimal.ZERO);
        location.setLongitude(BigDecimal.TEN);
        return location;
    }

    public static Map<Item, Integer> toInventory() {
        var inventory = new HashMap<Item, Integer>();
        inventory.put(Item.GUN, 10);
        inventory.put(Item.MUNITION, 10);
        inventory.put(Item.WATER, 10);
        inventory.put(Item.FOOD, 10);
        return inventory;
    }

    public static RebelDetailDTO toCreateRebel(MockMvc mockMvc) throws Exception {
        var content = toRebel();
        var postRequest = post("/v1/rebel")
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValueAsString(content));
        var response = mockMvc.perform(postRequest)
                .andReturn()
                .getResponse()
                .getContentAsString();
        return parse(response, RebelDetailDTO.class);
    }

}
