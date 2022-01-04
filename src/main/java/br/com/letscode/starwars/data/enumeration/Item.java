package br.com.letscode.starwars.data.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Item {

    GUN(4),
    MUNITION(3),
    WATER(2),
    FOOD(1);

    private final Integer points;

}
