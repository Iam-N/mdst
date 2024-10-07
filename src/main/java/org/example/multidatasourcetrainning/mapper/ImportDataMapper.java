package org.example.multidatasourcetrainning.mapper;

import lombok.experimental.UtilityClass;
import org.apache.commons.csv.CSVRecord;
import org.example.multidatasourcetrainning.db1.entities.ReadAndWrite;
import org.example.multidatasourcetrainning.enums.SimpleEnum;
import org.example.multidatasourcetrainning.enums.TableHeaders;

import java.time.LocalDateTime;

@UtilityClass
public class ImportDataMapper {
    public ReadAndWrite mapData(CSVRecord record) {
        return ReadAndWrite.builder()
                .id(Long.valueOf(record.get(TableHeaders.ID.name())))
                .field(record.get(TableHeaders.COl1.name()))
                .anotherField(record.get(TableHeaders.COl2.name()))
                .doubleField(Double.valueOf(record.get(TableHeaders.COl3.name())))
                .dateCreated(LocalDateTime.parse(record.get(TableHeaders.DATE_CREATED.name())))
                .simpleEnum(SimpleEnum.ENUM)
                .build();
    }
}
