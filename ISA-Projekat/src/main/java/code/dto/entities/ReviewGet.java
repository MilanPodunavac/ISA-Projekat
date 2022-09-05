package code.dto.entities;

import code.model.Client;
import code.model.base.SaleEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewGet {
    private Integer id;
    private int grade;
    private String description;
    private boolean approved;
    private Client client;
    private SaleEntity saleEntity;
}
