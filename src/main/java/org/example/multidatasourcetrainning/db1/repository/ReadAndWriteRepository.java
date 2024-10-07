package org.example.multidatasourcetrainning.db1.repository;

import jakarta.persistence.PersistenceContext;
import org.example.multidatasourcetrainning.db1.entities.ReadAndWrite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@PersistenceContext(unitName = "db1")
public interface ReadAndWriteRepository extends JpaRepository<ReadAndWrite, Long> {
    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE this_is_a_table", nativeQuery = true)
    void truncateTable();
}
