package org.example.multidatasourcetrainning.db1.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Deprecated
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Address implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String street;

    private String city;

    private String state;

    private String zip;

    private String country;

    @ManyToOne
    private Users user;
}
