package br.com.letscode.starwars.data.mapper;

import br.com.letscode.starwars.StarWarsProperties;
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
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class RebelMapper {

    @Autowired
    private StarWarsProperties properties;

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "reportedTraitorQuantity", constant = "0"),
            @Mapping(target = "inventory", ignore = true),
            @Mapping(target = "location", ignore = true)
    })
    public abstract Rebel toRebel(RebelDTO source);

    @Mappings({
            @Mapping(target = "traitor", expression = "java(isRebelTraitor(source))")
    })
    public abstract RebelDetailDTO toRebelDetailDTO(Rebel source);

    @Mappings({
            @Mapping(target = "rebel", source = "rebel"),
            @Mapping(target = "item", source = "item"),
            @Mapping(target = "quantity", source = "quantity")
    })
    public abstract RebelItem toRebelItem(Rebel rebel, Item item, Integer quantity);

    @Mappings({
            @Mapping(target = "rebel", source = "rebel"),
            @Mapping(target = "latitude", source = "location.latitude"),
            @Mapping(target = "longitude", source = "location.longitude"),
            @Mapping(target = "description", source = "location.description")
    })
    public abstract RebelLocation toRebelLocationLocation(Rebel rebel, RebelLocationDTO location);

    public abstract RebelLocationDTO toRebelLocationLocationDTO(RebelLocation source);

    public List<RebelItem> toRebelItems(Rebel rebel, Map<Item, Integer> source) {
        return source.keySet().stream()
                .map(item -> toRebelItem(rebel, item, source.get(item)))
                .collect(Collectors.toList());
    }

    protected Map<Item, Integer> toInventory(List<RebelItem> source) {
        return source.stream().collect(Collectors.toMap(RebelItem::getItem, RebelItem::getQuantity));
    }

    protected Boolean isRebelTraitor(Rebel source) {
        return source.getReportedTraitorQuantity() >= properties.getMaximumTraitorReports();
    }

}
