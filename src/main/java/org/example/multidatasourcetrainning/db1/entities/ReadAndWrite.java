package org.example.multidatasourcetrainning.db1.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.multidatasourcetrainning.enums.SimpleEnum;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "this_is_a_table")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ReadAndWrite implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String field;

    private String anotherField;

    private Double doubleField;

    @Enumerated(EnumType.STRING)
    private SimpleEnum simpleEnum;

    private LocalDateTime dateCreated;
}
