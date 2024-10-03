package org.example.multidatasourcetrainning.dto;

import lombok.Data;
import org.example.multidatasourcetrainning.enums.Role;

@Deprecated
@Data
public class AddNewDTO {
    private int id;
    private String name;
    private Role role;
    private String type;
}
