package br.com.letscode.starwars.service.trade;

import br.com.letscode.starwars.data.domain.Rebel;
import br.com.letscode.starwars.data.dto.RebelItemDTO;
import br.com.letscode.starwars.data.dto.TradeDTO;
import br.com.letscode.starwars.data.dto.TradeRebelDTO;
import br.com.letscode.starwars.data.mapper.RebelMapper;
import br.com.letscode.starwars.data.repository.RebelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TradeService {

    private final RebelMapper mapper;
    private final RebelRepository rebelRepository;

    public void trade(TradeDTO request) {
        var firstRebel = findRebel(request.getFirstRebel());
        var secondRebel = findRebel(request.getSecondRebel());

        addInventory(firstRebel, request.getSecondRebel().getItemsToTrade());
        addInventory(secondRebel, request.getFirstRebel().getItemsToTrade());
        removeInventory(firstRebel, request.getFirstRebel().getItemsToTrade());
        removeInventory(secondRebel, request.getSecondRebel().getItemsToTrade());

        rebelRepository.save(firstRebel);
        rebelRepository.save(secondRebel);
    }

    private Rebel findRebel(TradeRebelDTO rebel) {
        return rebelRepository.findById(rebel.getId()).orElseThrow(NoResultException::new);
    }

    private void addInventory(Rebel rebel, List<RebelItemDTO> inventory) {
        for (RebelItemDTO rebelItem : inventory) {
            rebel.getInventory().forEach(item -> {
                if (item.getItem().equals(rebelItem.getItem())) {
                    var quantity = item.getQuantity() + rebelItem.getQuantity();
                    item.setQuantity(quantity);
                }
            });
        }
    }

    private void removeInventory(Rebel rebel, List<RebelItemDTO> inventory) {
        for (RebelItemDTO rebelItem : inventory) {
            rebel.getInventory().forEach(item -> {
                if (item.getItem().equals(rebelItem.getItem())) {
                    var quantity = Math.abs(item.getQuantity() - rebelItem.getQuantity());
                    item.setQuantity(quantity);
                }
            });
        }
    }

}
