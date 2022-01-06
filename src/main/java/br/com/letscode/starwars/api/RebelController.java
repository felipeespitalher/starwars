package br.com.letscode.starwars.api;

import br.com.letscode.starwars.data.dto.RebelDTO;
import br.com.letscode.starwars.data.dto.RebelDetailDTO;
import br.com.letscode.starwars.data.dto.RebelLocationDTO;
import br.com.letscode.starwars.service.RebelFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping(value = "v1/rebel", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class RebelController {

    private final RebelFacade facade;

    @PostMapping
    public ResponseEntity<RebelDetailDTO> create(@Valid @RequestBody RebelDTO request) {
        var rebel = facade.create(request);
        return status(HttpStatus.CREATED).body(rebel);
    }

    @GetMapping("{id}")
    public ResponseEntity<RebelDetailDTO> get(@PathVariable Long id) {
        var rebel = facade.get(id);
        return ResponseEntity.ok(rebel);
    }

    @PatchMapping("{id}/location")
    public ResponseEntity<RebelDetailDTO> updateLocation(@PathVariable Long id, @Valid @RequestBody RebelLocationDTO request) {
        facade.updateLocation(id, request);
        return get(id);
    }

    @PutMapping("{id}/traitor")
    public ResponseEntity<RebelDetailDTO> reportTraitor(@PathVariable Long id) {
        facade.reportTraitor(id);
        return get(id);
    }

}
