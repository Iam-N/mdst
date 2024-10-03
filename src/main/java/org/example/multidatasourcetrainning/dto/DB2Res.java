package org.example.multidatasourcetrainning.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.example.multidatasourcetrainning.db2.entities.ReadOnly;
import org.example.multidatasourcetrainning.enums.SimpleEnum;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class DB2Res {
    private List<ReadOnly> db2data;
}
