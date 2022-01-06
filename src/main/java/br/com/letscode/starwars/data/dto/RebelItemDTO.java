package br.com.letscode.starwars.data.dto;

import br.com.letscode.starwars.data.enumeration.Item;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RebelItemDTO {

    @NotNull(message = "{rebel.inventory.item.notNull}")
    private Item item;

    @NotNull(message = "{rebel.inventory.quantity.notNull}")
    private Integer quantity;

}
