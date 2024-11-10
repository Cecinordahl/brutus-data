package com.ceci.projects.brutusdata.repository;

import com.ceci.projects.brutusdata.model.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT user FROM UserEntity user WHERE " +
            "(:firstName IS NULL OR user.firstName LIKE %:firstName%) AND " +
            "(:lastName IS NULL OR user.lastName LIKE %:lastName%) AND " +
            "(:city IS NULL OR user.city LIKE %:city%)")
    List<UserEntity> findBySearchCriteria(String firstName, String lastName, String city, Pageable pageable);
}