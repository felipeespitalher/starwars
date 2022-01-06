package br.com.letscode.starwars.service;

import br.com.letscode.starwars.data.dto.RebelDTO;
import br.com.letscode.starwars.data.dto.RebelDetailDTO;
import br.com.letscode.starwars.data.dto.RebelLocationDTO;
import br.com.letscode.starwars.service.rebel.RebelCreateService;
import br.com.letscode.starwars.service.rebel.RebelGetService;
import br.com.letscode.starwars.service.rebel.RebelReportTraitorService;
import br.com.letscode.starwars.service.rebel.RebelUpdateLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RebelFacade {

    private final RebelCreateService createService;
    private final RebelGetService getService;
    private final RebelUpdateLocationService updateLocationService;
    private final RebelReportTraitorService reportTraitorService;

    public RebelDetailDTO create(RebelDTO request) {
        return createService.create(request);
    }

    public RebelDetailDTO get(Long id) {
        return getService.get(id);
    }

    public void updateLocation(Long id, RebelLocationDTO request) {
        updateLocationService.updateLocation(id, request);
    }

    public void reportTraitor(Long id) {
        reportTraitorService.reportTraitor(id);
    }

}
