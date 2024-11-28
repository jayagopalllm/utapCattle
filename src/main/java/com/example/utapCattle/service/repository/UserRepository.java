package com.example.utapCattle.service.repository;

import com.example.utapCattle.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT nextval('user_id_seq')", nativeQuery = true)
    Long getNextSequenceValue();

    boolean existsByUserName(String userName);

}
