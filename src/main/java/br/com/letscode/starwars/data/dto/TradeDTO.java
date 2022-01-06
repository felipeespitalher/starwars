package br.com.letscode.starwars.data.dto;

import br.com.letscode.starwars.validation.InventoryAssert;
import br.com.letscode.starwars.validation.TradeAssert;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@TradeAssert
public class TradeDTO {

    @Valid
    @NotNull(message = "{trade.firstRebel.notNull}")
    @InventoryAssert
    private TradeRebelDTO firstRebel;

    @Valid
    @NotNull(message = "{trade.secondRebel.notNull}")
    @InventoryAssert
    private TradeRebelDTO secondRebel;

}
