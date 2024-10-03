package org.example.multidatasourcetrainning.dto;

import lombok.Data;
import org.example.multidatasourcetrainning.enums.SimpleEnum;

@Data
public class DB1Add {
    private String field;
    private String anotherField;
    private Double doubleField;
    private SimpleEnum simpleEnum;
}
