package org.example.multidatasourcetrainning.db2.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.multidatasourcetrainning.enums.Role;

import java.io.Serializable;

@Deprecated
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notusers")
@Data
@ToString
public class NotUsers implements Serializable {
    @Id
    private Integer id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;
}
