package org.example.multidatasourcetrainning.db2.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Deprecated
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "products")
@Getter
@Setter
public class Products implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    private Double price;

    @ManyToOne
    private Category category;
}
