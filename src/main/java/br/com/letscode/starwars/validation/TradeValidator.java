package br.com.letscode.starwars.validation;

import br.com.letscode.starwars.data.dto.RebelItemDTO;
import br.com.letscode.starwars.data.dto.TradeDTO;
import br.com.letscode.starwars.data.dto.TradeRebelDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class TradeValidator implements ConstraintValidator<TradeAssert, TradeDTO> {

    @Override
    public boolean isValid(TradeDTO value, ConstraintValidatorContext context) {
        if (value == null || assertTrader(value.getFirstRebel()) || assertTrader(value.getSecondRebel())) {
            return false;
        }
        var firstTraderPoints = sumTraderPoints(value.getFirstRebel().getItemsToTrade());
        var secondTraderPoints = sumTraderPoints(value.getSecondRebel().getItemsToTrade());
        return firstTraderPoints.equals(secondTraderPoints);
    }

    private Integer sumTraderPoints(List<RebelItemDTO> itemsToTrade) {
        return itemsToTrade.stream()
                .map(item -> item.getItem().getPoints() * item.getQuantity())
                .reduce(Integer::sum)
                .orElse(0);
    }

    private Boolean assertTrader(TradeRebelDTO trader) {
        return trader == null || trader.getItemsToTrade() == null;
    }

}
