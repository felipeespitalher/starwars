package br.com.letscode.starwars.service.summary;

import br.com.letscode.starwars.StarWarsProperties;
import br.com.letscode.starwars.data.domain.RebelItem;
import br.com.letscode.starwars.data.domain.RebelItem_;
import br.com.letscode.starwars.data.domain.Rebel_;
import br.com.letscode.starwars.data.dto.SummaryLostPointsDTO;
import br.com.letscode.starwars.data.dto.SummaryTraitorItemsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
@RequiredArgsConstructor
public class SummaryLostPointsTraitorService {

    private final StarWarsProperties properties;
    private final EntityManager entityManager;

    public SummaryLostPointsDTO lostPointsByTraitors() {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var query = criteriaBuilder.createQuery(SummaryTraitorItemsDTO.class);
        var from = query.from(RebelItem.class);
        var rebel = from.join(RebelItem_.REBEL);
        query.groupBy(from.get(RebelItem_.ITEM));
        query.select(criteriaBuilder.construct(
                SummaryTraitorItemsDTO.class,
                from.get(RebelItem_.ITEM),
                criteriaBuilder.sum(from.get(RebelItem_.QUANTITY))
        ));
        query.where(
                criteriaBuilder.greaterThanOrEqualTo(rebel.get(Rebel_.REPORTED_TRAITOR_QUANTITY), properties.getMaximumTraitorReports())
        );
        var traitorPoints = entityManager.createQuery(query).getResultList();
        var points = traitorPoints.stream()
                .map(item -> item.getItem().getPoints() * item.getQuantity())
                .reduce(Long::sum)
                .orElse(0L);
        return SummaryLostPointsDTO.builder().points(points).build();
    }

}
