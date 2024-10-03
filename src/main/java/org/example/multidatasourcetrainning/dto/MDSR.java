package org.example.multidatasourcetrainning.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.multidatasourcetrainning.db1.entities.Users;
import org.example.multidatasourcetrainning.db2.entities.NotUsers;

import java.util.List;

@Deprecated
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MDSR {
    private List<Users> users;
    private List<NotUsers> notUsers;
}
