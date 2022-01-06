package br.com.letscode.starwars.service.summary;

import br.com.letscode.starwars.StarWarsProperties;
import br.com.letscode.starwars.data.domain.Rebel;
import br.com.letscode.starwars.data.domain.Rebel_;
import br.com.letscode.starwars.data.dto.SummaryTraitorRebelsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
@RequiredArgsConstructor
public class SummaryRebelTraitorService {

    private final StarWarsProperties properties;
    private final EntityManager entityManager;

    public SummaryTraitorRebelsDTO traitorRebels() {
        var dto = new SummaryTraitorRebelsDTO();
        dto.setRebels(countRebels(false));
        dto.setTraitors(countRebels(true));
        return dto;
    }

    private Long countRebels(Boolean traitor) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Long.class);

        var from = criteriaQuery.from(Rebel.class);
        criteriaQuery.select(criteriaBuilder.count(from));
        if (traitor) {
            criteriaQuery.where(criteriaBuilder.greaterThanOrEqualTo(from.get(Rebel_.REPORTED_TRAITOR_QUANTITY), properties.getMaximumTraitorReports()));
        } else {
            criteriaQuery.where(criteriaBuilder.lessThan(from.get(Rebel_.REPORTED_TRAITOR_QUANTITY), properties.getMaximumTraitorReports()));
        }
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

}
