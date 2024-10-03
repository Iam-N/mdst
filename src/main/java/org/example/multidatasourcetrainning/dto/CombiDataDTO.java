package org.example.multidatasourcetrainning.dto;

import lombok.Data;
import org.example.multidatasourcetrainning.enums.Role;

@Deprecated
@Data
public class CombiDataDTO {
    private Integer userId;
    private String username;
    private Role userRole;

    private Integer notUserId;
    private String notUsername;
    private Role notUserRole;
}
