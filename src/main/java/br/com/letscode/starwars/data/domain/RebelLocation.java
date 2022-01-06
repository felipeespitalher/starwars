package br.com.letscode.starwars.data.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Getter
@Table(name = "rebel_locations")
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RebelLocation implements Serializable {

    @Id
    @OneToOne
    @JoinColumn(name = "rebel_id")
    @EqualsAndHashCode.Include
    private Rebel rebel;

    @Column(nullable = false)
    private BigDecimal latitude;

    @Column(nullable = false)
    private BigDecimal longitude;

    @Column(nullable = false)
    private String description;

}
