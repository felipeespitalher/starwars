package br.com.letscode.starwars.api;

import br.com.letscode.starwars.data.dto.SummaryItemAverageDTO;
import br.com.letscode.starwars.data.dto.SummaryLostPointsDTO;
import br.com.letscode.starwars.data.dto.SummaryTraitorRebelsDTO;
import br.com.letscode.starwars.service.SummaryFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(value = "v1/summary", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class SummaryController {

    private final SummaryFacade facade;

    @GetMapping("traitorRebels")
    public ResponseEntity<SummaryTraitorRebelsDTO> traitorRebels() {
        var response = facade.traitorRebels();
        return ResponseEntity.ok(response);
    }

    @GetMapping("averageItemsByRebel")
    public ResponseEntity<List<SummaryItemAverageDTO>> averageItemsByRebel() {
        var response = facade.averageItemsByRebel();
        return ResponseEntity.ok(response);
    }

    @GetMapping("lostPointsByTraitors")
    public ResponseEntity<SummaryLostPointsDTO> lostPointsByTraitors() {
        var response = facade.lostPointsByTraitors();
        return ResponseEntity.ok(response);
    }

}
