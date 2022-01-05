package br.com.letscode.starwars.api;

import br.com.letscode.starwars.data.dto.RebelDTO;
import br.com.letscode.starwars.data.dto.RebelLocationDTO;
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

    @PostMapping
    public ResponseEntity<RebelDTO> create(@Valid @RequestBody RebelDTO request) {
        return status(HttpStatus.CREATED).body(request);
    }

    @GetMapping("{id}")
    public ResponseEntity<RebelDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(new RebelDTO());
    }

    @PatchMapping("{id}/location")
    public ResponseEntity<Void> updateLocation(@PathVariable Long id, @Valid @RequestBody RebelLocationDTO request) {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("{id}/traitor")
    public ResponseEntity<Void> reportTraitor(@PathVariable Long id, @RequestParam Long traitor) {
        return ResponseEntity.ok().build();
    }

}
