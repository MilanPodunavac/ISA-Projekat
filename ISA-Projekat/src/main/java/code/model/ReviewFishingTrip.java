package code.model;

import code.model.base.SaleEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ReviewFishingTrip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private int grade;
    @Column
    private String description;
    @Column
    private boolean approved;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    @JsonBackReference
    private Client client;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fishingTrip_id")
    @JsonBackReference
    private FishingTrip fishingTrip;
}
