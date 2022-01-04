package br.com.letscode.starwars.data.domain;


import br.com.letscode.starwars.data.enumeration.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "rebel_items")
@Setter
public class RebelItem {

    @Id
    @ManyToOne
    @JoinColumn(name = "rebel_id")
    private Rebel rebel;

    @Id
    private Item item;

    @Column(nullable = false)
    private Integer quantity;

}
