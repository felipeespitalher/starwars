package br.com.letscode.starwars.service.rebel;

import br.com.letscode.starwars.data.repository.RebelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;

@Service
@RequiredArgsConstructor
public class RebelReportTraitorService {

    private final RebelRepository repository;

    public void reportTraitor(Long id) {
        var rebel = repository.findById(id).orElseThrow(NoResultException::new);

        var reportedTraitorQuantity = rebel.getReportedTraitorQuantity();
        rebel.setReportedTraitorQuantity(++reportedTraitorQuantity);

        repository.save(rebel);
    }

}
