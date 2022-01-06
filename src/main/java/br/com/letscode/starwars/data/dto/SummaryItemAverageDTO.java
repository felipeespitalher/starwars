package br.com.letscode.starwars.data.dto;

import br.com.letscode.starwars.data.enumeration.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SummaryItemAverageDTO {

    private Item item;

    private Double quantity;

}
