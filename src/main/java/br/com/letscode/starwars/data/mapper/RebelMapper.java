package br.com.letscode.starwars.data.mapper;

import br.com.letscode.starwars.data.domain.Rebel;
import br.com.letscode.starwars.data.domain.RebelItem;
import br.com.letscode.starwars.data.domain.RebelLocation;
import br.com.letscode.starwars.data.dto.RebelDTO;
import br.com.letscode.starwars.data.dto.RebelDetailDTO;
import br.com.letscode.starwars.data.dto.RebelLocationDTO;
import br.com.letscode.starwars.data.enumeration.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RebelMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "reportedTraitorQuantity", constant = "0"),
            @Mapping(target = "inventory", ignore = true),
            @Mapping(target = "location", ignore = true)
    })
    Rebel toRebel(RebelDTO source);

    @Mappings({
            @Mapping(target = "traitor", constant = "false")
    })
    RebelDetailDTO toRebelDetailDTO(Rebel rebel);

    @Mappings({
            @Mapping(target = "rebel", source = "rebel"),
            @Mapping(target = "item", source = "item"),
            @Mapping(target = "quantity", source = "quantity")
    })
    RebelItem toRebelItem(Rebel rebel, Item item, Integer quantity);

    @Mappings({
            @Mapping(target = "rebel", source = "rebel"),
            @Mapping(target = "latitude", source = "location.latitude"),
            @Mapping(target = "longitude", source = "location.longitude"),
            @Mapping(target = "description", source = "location.description")
    })
    RebelLocation toRebelLocationLocation(Rebel rebel, RebelLocationDTO location);

    RebelLocationDTO toRebelLocationLocationDTO(RebelLocation source);

    default List<RebelItem> toRebelItems(Rebel rebel, Map<Item, Integer> source) {
        return source.keySet().stream()
                .map(item -> toRebelItem(rebel, item, source.get(item)))
                .collect(Collectors.toList());
    }

    default Map<Item, Integer> toInventory(List<RebelItem> source) {
        return source.stream().collect(Collectors.toMap(RebelItem::getItem, RebelItem::getQuantity));
    }

}
