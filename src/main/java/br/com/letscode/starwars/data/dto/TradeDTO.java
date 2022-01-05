package br.com.letscode.starwars.data.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TradeDTO {

    @Valid
    @NotNull(message = "{trade.firstRebel.notNull}")
    private TradeRebelDTO firstRebel;

    @Valid
    @NotNull(message = "{trade.secondRebel.notNull}")
    private TradeRebelDTO secondRebel;

}
