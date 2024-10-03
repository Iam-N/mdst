package org.example.multidatasourcetrainning.db2.repository;

import org.example.multidatasourcetrainning.db2.entities.NotUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Deprecated
@Repository
public interface NotUsersRepository extends JpaRepository<NotUsers, Integer> {
}
