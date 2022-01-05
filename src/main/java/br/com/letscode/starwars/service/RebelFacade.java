package br.com.letscode.starwars.service;

import br.com.letscode.starwars.data.dto.RebelDTO;
import br.com.letscode.starwars.data.dto.RebelDetailDTO;
import br.com.letscode.starwars.data.dto.RebelLocationDTO;
import br.com.letscode.starwars.service.rebel.RebelCreateService;
import br.com.letscode.starwars.service.rebel.RebelGetService;
import br.com.letscode.starwars.service.rebel.RebelUpdateLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RebelFacade {

    private final RebelCreateService createService;
    private final RebelGetService getService;
    private final RebelUpdateLocationService updateLocationService;

    public RebelDetailDTO create(RebelDTO request) {
        return createService.create(request);
    }

    public RebelDetailDTO get(Long id) {
        return getService.get(id);
    }

    public List<RebelDetailDTO> search() {
        return null;
    }


    public void updateLocation(Long id, RebelLocationDTO request) {
        updateLocationService.updateLocation(id, request);
    }

    public void reportTraitor(Long id) {

    }

}
