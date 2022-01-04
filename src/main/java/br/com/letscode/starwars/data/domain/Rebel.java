package br.com.letscode.starwars.data.domain;

import br.com.letscode.starwars.data.enumeration.Gender;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Table(name = "rebels")
@Entity
public class Rebel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate birth;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToOne(mappedBy = "rebel", optional = false)
    private RebelLocation location;

    @OneToMany(mappedBy = "rebel", orphanRemoval = true)
    private List<RebelItem> inventory;

    @Column(nullable = false)
    private Integer reportedBetrayalQuantity;

}
