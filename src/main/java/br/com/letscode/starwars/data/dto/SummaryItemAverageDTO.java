package br.com.letscode.starwars.data.dto;

import br.com.letscode.starwars.data.enumeration.Item;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SummaryItemAverageDTO {

    private final Item item;

    private final Double quantity;

}
