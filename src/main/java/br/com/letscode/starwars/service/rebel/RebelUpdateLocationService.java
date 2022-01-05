package br.com.letscode.starwars.service.rebel;

import br.com.letscode.starwars.data.dto.RebelLocationDTO;
import br.com.letscode.starwars.data.mapper.RebelMapper;
import br.com.letscode.starwars.data.repository.RebelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;

@Service
@RequiredArgsConstructor
public class RebelUpdateLocationService {

    private final RebelMapper mapper;
    private final RebelRepository repository;

    public void updateLocation(Long id, RebelLocationDTO request) {
        var rebel = repository.findById(id).orElseThrow(NoResultException::new);
        var location = mapper.toRebelLocationLocation(rebel, request);
        rebel.setLocation(location);
        repository.save(rebel);
    }

}
