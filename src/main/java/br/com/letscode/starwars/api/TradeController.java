package br.com.letscode.starwars.api;

import br.com.letscode.starwars.data.dto.TradeDTO;
import br.com.letscode.starwars.service.trade.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "v1/trade", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TradeController {

    private final TradeService service;

    @PostMapping
    public ResponseEntity<?> trade(@Valid @RequestBody TradeDTO request) {
        service.trade(request);
        return ResponseEntity.accepted().build();
    }

}
