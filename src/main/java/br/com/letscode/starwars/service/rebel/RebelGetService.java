package br.com.letscode.starwars.service.rebel;

import br.com.letscode.starwars.data.dto.RebelDetailDTO;
import br.com.letscode.starwars.data.mapper.RebelMapper;
import br.com.letscode.starwars.data.repository.RebelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;

@Service
@RequiredArgsConstructor
public class RebelGetService {

    private final RebelMapper mapper;
    private final RebelRepository repository;

    public RebelDetailDTO get(Long id) {
        var rebel = repository.findById(id).orElseThrow(NoResultException::new);
        return mapper.toRebelDetailDTO(rebel);
    }

}
