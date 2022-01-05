package br.com.letscode.starwars.service.rebel;

import br.com.letscode.starwars.data.dto.RebelDTO;
import br.com.letscode.starwars.data.dto.RebelDetailDTO;
import br.com.letscode.starwars.data.mapper.RebelMapper;
import br.com.letscode.starwars.data.repository.RebelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RebelCreateService {

    private final RebelMapper mapper;
    private final RebelRepository repository;

    public RebelDetailDTO create(RebelDTO request) {
        var rebel = mapper.toRebel(request);
        rebel.setInventory(mapper.toRebelItems(rebel, request.getInventory()));
        rebel.setLocation(mapper.toRebelLocationLocation(rebel, request.getLocation()));
        rebel = repository.save(rebel);
        return mapper.toRebelDetailDTO(rebel);
    }

}
