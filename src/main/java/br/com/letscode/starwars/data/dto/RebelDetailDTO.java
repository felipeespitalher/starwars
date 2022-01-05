package br.com.letscode.starwars.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RebelDetailDTO extends RebelDTO {

    private Long id;
    private Boolean traitor;

}
