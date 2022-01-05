package br.com.letscode.starwars.data.domain;


import br.com.letscode.starwars.data.enumeration.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Table(name = "rebel_items")
@Setter
public class RebelItem implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "rebel_id")
    private Rebel rebel;

    @Id
    @Enumerated(EnumType.STRING)
    private Item item;

    @Column(nullable = false)
    private Integer quantity;

}
