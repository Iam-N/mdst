package org.example.multidatasourcetrainning.db1.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.multidatasourcetrainning.enums.Role;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Deprecated
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Data
@ToString
public class Users implements Serializable {
    @Id
    private Integer id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private Set<Address> addresses = new HashSet<>();
}
