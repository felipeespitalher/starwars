package br.com.letscode.starwars;

import br.com.letscode.starwars.data.dto.RebelDTO;
import br.com.letscode.starwars.data.dto.RebelLocationDTO;
import br.com.letscode.starwars.data.enumeration.Gender;
import br.com.letscode.starwars.data.enumeration.Item;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

public class RebelUtils {

    public static RebelDTO toRebel() {
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
        return rebel;
    }

}
