package br.com.letscode.starwars.service.summary;

import br.com.letscode.starwars.data.domain.RebelItem;
import br.com.letscode.starwars.data.domain.RebelItem_;
import br.com.letscode.starwars.data.dto.SummaryItemAverageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SummaryAverageItemsService {

    private final EntityManager entityManager;

    public List<SummaryItemAverageDTO> averageItemsByRebel() {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var query = criteriaBuilder.createQuery(SummaryItemAverageDTO.class);
        var from = query.from(RebelItem.class);
        query.select(criteriaBuilder.construct(
                SummaryItemAverageDTO.class,
                from.get(RebelItem_.ITEM),
                criteriaBuilder.avg(from.get(RebelItem_.QUANTITY))
        ));
        query.groupBy(from.get(RebelItem_.ITEM));
        return entityManager.createQuery(query).getResultList();
    }

}
