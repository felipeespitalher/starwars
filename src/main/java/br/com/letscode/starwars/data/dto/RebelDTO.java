package br.com.letscode.starwars.data.dto;

import br.com.letscode.starwars.data.enumeration.Gender;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class RebelDTO {

    @NotEmpty(message = "{rebel.name.notEmpty}")
    private String name;

    @Past(message = "{rebel.birth.past}")
    @NotNull(message = "{rebel.birth.notNull}")
    private LocalDate birth;

    @NotNull(message = "{rebel.gender.notNull}")
    private Gender gender;

    @NotNull(message = "{rebel.inventory.notNull}")
    private List<RebelItemDTO> inventory;

    @Valid
    @NotNull(message = "{rebel.location.notNull}")
    private RebelLocationDTO location;

}
