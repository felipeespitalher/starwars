package br.com.letscode.starwars.data.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Table(name = "rebel_locations")
@Setter
public class RebelLocation {

    @Id
    @OneToOne
    @JoinColumn(name = "rebel_id")
    private Rebel rebel;

    @Column(nullable = false)
    private BigDecimal latitude;

    @Column(nullable = false)
    private BigDecimal longitude;

    @Column(nullable = false)
    private String description;

}
