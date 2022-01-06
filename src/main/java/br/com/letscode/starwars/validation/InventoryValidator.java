package br.com.letscode.starwars.validation;

import br.com.letscode.starwars.StarWarsProperties;
import br.com.letscode.starwars.configuration.messages.MessageProvider;
import br.com.letscode.starwars.data.domain.Rebel;
import br.com.letscode.starwars.data.dto.RebelItemDTO;
import br.com.letscode.starwars.data.dto.TradeRebelDTO;
import br.com.letscode.starwars.data.repository.RebelRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class InventoryValidator implements ConstraintValidator<InventoryAssert, TradeRebelDTO> {

    @Autowired
    private RebelRepository repository;

    @Autowired
    private MessageProvider messageProvider;

    @Autowired
    private StarWarsProperties properties;

    @Override
    public boolean isValid(TradeRebelDTO value, ConstraintValidatorContext context) {
        if (checkIsNull(value, context)) {
            return false;
        }
        var rebel = searchRebel(value.getId(), context);
        return rebel.isPresent() && assertIsNotTraitor(context, rebel.get()) && assertValidInventory(context, value, rebel.get());
    }

    private boolean assertValidInventory(ConstraintValidatorContext context, TradeRebelDTO value, Rebel rebel) {
        var inventory = rebel.getInventory();
        for (RebelItemDTO tradeItem : value.getItemsToTrade()) {
            var inventoryItem = inventory.stream().filter(item -> item.getItem().equals(tradeItem.getItem())).findFirst();
            if (inventoryItem.isEmpty()) {
                addErrorMessageToContext(context, "trade.rebel.doesntHaveItem", tradeItem.getItem().name());
                return false;
            }
            if (inventoryItem.get().getQuantity() < tradeItem.getQuantity()) {
                addErrorMessageToContext(context, "trade.rebel.doestHaveQuantity", tradeItem.getQuantity().toString(), tradeItem.getItem().name());
                return false;
            }
        }
        return true;
    }

    private boolean assertIsNotTraitor(ConstraintValidatorContext context, Rebel rebel) {
        if (rebel.getReportedTraitorQuantity() >= properties.getMaximumTraitorReports()) {
            addErrorMessageToContext(context, "trade.rebel.traitor.notAllowed");
            return false;
        } else {
            return true;
        }
    }

    private Optional<Rebel> searchRebel(Long id, ConstraintValidatorContext context) {
        var rebel = repository.findById(id);
        if (rebel.isEmpty()) {
            addErrorMessageToContext(context, "trade.rebel.notFound");
            return Optional.empty();
        } else {
            return rebel;
        }
    }

    private boolean checkIsNull(TradeRebelDTO value, ConstraintValidatorContext context) {
        if (value == null || value.getId() == null || value.getItemsToTrade() == null) {
            addErrorMessageToContext(context, "trade.rebel.notNull");
            return true;
        } else {
            return false;
        }
    }

    private void addErrorMessageToContext(ConstraintValidatorContext context, String code, String... parameters) {
        var message = messageProvider.getMessage(code, parameters);
        context
                .buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
    }

}
