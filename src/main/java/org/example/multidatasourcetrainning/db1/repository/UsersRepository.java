package org.example.multidatasourcetrainning.db1.repository;

import org.example.multidatasourcetrainning.db1.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Deprecated
@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
}
