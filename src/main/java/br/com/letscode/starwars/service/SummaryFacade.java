package br.com.letscode.starwars.service;

import br.com.letscode.starwars.data.dto.SummaryItemAverageDTO;
import br.com.letscode.starwars.data.dto.SummaryLostPointsDTO;
import br.com.letscode.starwars.data.dto.SummaryTraitorRebelsDTO;
import br.com.letscode.starwars.service.summary.SummaryAverageItemsService;
import br.com.letscode.starwars.service.summary.SummaryLostPointsTraitorService;
import br.com.letscode.starwars.service.summary.SummaryRebelTraitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SummaryFacade {

    private final SummaryRebelTraitorService rebelTraitorService;
    private final SummaryAverageItemsService averageItemsService;
    private final SummaryLostPointsTraitorService lostPointsTraitorService;

    public SummaryTraitorRebelsDTO traitorRebels() {
        return rebelTraitorService.traitorRebels();
    }

    public List<SummaryItemAverageDTO> averageItemsByRebel() {
        return averageItemsService.averageItemsByRebel();
    }

    public SummaryLostPointsDTO lostPointsByTraitors() {
        return lostPointsTraitorService.lostPointsByTraitors();
    }

}
