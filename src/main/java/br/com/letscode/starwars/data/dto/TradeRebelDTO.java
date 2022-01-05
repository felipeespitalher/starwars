package br.com.letscode.starwars.data.dto;

import br.com.letscode.starwars.data.enumeration.Item;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Getter
@Setter
public class TradeRebelDTO {

    @NotNull(message = "{trade.rebel.id.notNull}")
    private Long id;

    @NotNull(message = "{trade.rebel.itemsToTrade.notNull}")
    private Map<Item, Integer> itemsToTrade;

}
