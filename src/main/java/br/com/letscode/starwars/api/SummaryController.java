package br.com.letscode.starwars.api;

import br.com.letscode.starwars.data.enumeration.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.status;


@RestController
@RequestMapping(value = "v1/summary", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class SummaryController {

    @GetMapping("traitors")
    public ResponseEntity<?> traitors() {
        return status(HttpStatus.CREATED).build();
    }

    @GetMapping("rebels")
    public ResponseEntity<?> rebels() {
        return status(HttpStatus.CREATED).build();
    }

    @GetMapping("averageItemsByRebel")
    public ResponseEntity<?> averageItemsByRebel(@RequestParam(required = false) Item item) {
        return status(HttpStatus.CREATED).build();
    }

    @GetMapping("lostItemsByTraitors")
    public ResponseEntity<?> lostItemsByTraitors() {
        return status(HttpStatus.CREATED).build();
    }

}
