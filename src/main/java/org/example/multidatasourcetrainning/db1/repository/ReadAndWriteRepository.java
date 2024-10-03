package org.example.multidatasourcetrainning.db1.repository;

import jakarta.persistence.PersistenceContext;
import org.example.multidatasourcetrainning.db1.entities.ReadAndWrite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@PersistenceContext(unitName = "db1")
public interface ReadAndWriteRepository extends JpaRepository<ReadAndWrite, Long> {
}
