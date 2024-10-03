package org.example.multidatasourcetrainning.db2.repository;

import jakarta.persistence.PersistenceContext;
import org.example.multidatasourcetrainning.db2.entities.ReadOnly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@PersistenceContext(unitName = "db2")
public interface ReadOnlyRepository extends JpaRepository<ReadOnly, Long> {

}
