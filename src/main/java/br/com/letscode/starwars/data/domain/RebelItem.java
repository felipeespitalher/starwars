package br.com.letscode.starwars.data.domain;


import br.com.letscode.starwars.data.enumeration.Item;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Table(name = "rebel_items")
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RebelItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "rebel_id")
    @EqualsAndHashCode.Include
    private Rebel rebel;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @EqualsAndHashCode.Include
    private Item item;

    @Column(nullable = false)
    private Integer quantity;

}
