package br.com.letscode.starwars.data.dto;

import br.com.letscode.starwars.data.enumeration.Item;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SummaryItemAverageDTO {

    private Item item;

    private BigDecimal quantity;

}
