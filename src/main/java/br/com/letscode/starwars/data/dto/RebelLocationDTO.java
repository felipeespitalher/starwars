package br.com.letscode.starwars.data.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class RebelLocationDTO {

    @NotEmpty(message = "{rebel.location.description.notEmpty}")
    private String description;

    @NotNull(message = "{rebel.location.latitude.notNull}")
    private BigDecimal latitude;

    @NotNull(message = "{rebel.location.longitude.notNull}")
    private BigDecimal longitude;

}
