package org.example.multidatasourcetrainning.services;

import jakarta.persistence.EntityManager;
import org.example.multidatasourcetrainning.db1.entities.ReadAndWrite;
import org.example.multidatasourcetrainning.db1.repository.ReadAndWriteRepository;
import org.example.multidatasourcetrainning.db2.entities.ReadOnly;
import org.example.multidatasourcetrainning.db2.repository.ReadOnlyRepository;
import org.example.multidatasourcetrainning.dto.DB1Add;
import org.example.multidatasourcetrainning.dto.DB2Res;
import org.example.multidatasourcetrainning.utils.DB2AccessModifier;
import org.example.multidatasourcetrainning.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SyncDataSourceService {
    @Autowired
    private ReadAndWriteRepository rwRepository;

    @Autowired
    private ReadOnlyRepository roRepository;

    @Autowired
    private DB2AccessModifier AMQ;

    @Autowired
    private EntityManager entityManager;

    @Transactional(value = "transactionManager2", readOnly = true)
    public DB2Res findDataFromDatasource2() {
        List<ReadOnly> data = roRepository.findAll();
        return new DB2Res(data);
    }

    /**
     * Insert a new record to database 1, also put it in the database 2.<br/>
     * Throw exception if unable to insert to db2.
     *
     * @param dto
     * @throws IllegalAccessException
     * @throws SQLException
     */
    @Transactional
    public void addDataToDatasource1(DB1Add dto) throws IllegalAccessException, SQLException {
        if (DataUtils.nullField(dto)) throw new IllegalArgumentException("ඞ");

        ReadAndWrite rw = ReadAndWrite.builder()
                .field(dto.getField())
                .anotherField(dto.getAnotherField())
                .doubleField(dto.getDoubleField())
                .simpleEnum(dto.getSimpleEnum())
                .dateCreated(LocalDateTime.now())
                .build();

        ReadOnly ro = (ReadOnly) DataUtils.transferData(rw, new ReadOnly());

        rwRepository.save(rw);

        //Unlock db2 > insert data > lock db2
        AMQ.unlockDatabase();
        roRepository.save(ro);
        AMQ.lockDatabase();
    }
}
