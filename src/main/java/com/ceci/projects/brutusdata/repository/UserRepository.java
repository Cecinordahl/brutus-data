package com.ceci.projects.brutusdata.repository;

import com.ceci.projects.brutusdata.model.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("SELECT user FROM UserEntity user WHERE " +
            "(:firstName IS NULL OR user.firstName ILIKE %:firstName%) AND " +
            "(:lastName IS NULL OR user.lastName ILIKE %:lastName%) AND " +
            "(:city IS NULL OR user.city ILIKE %:city%) AND " +
            "(:minAge IS NULL OR user.age >= :minAge) AND " +
            "(:maxAge IS NULL OR user.age <= :maxAge) " +
            "ORDER BY user.lastName ASC")
    List<UserEntity> findBySearchCriteria(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("city") String city,
            @Param("minAge") Integer minAge,
            @Param("maxAge") Integer maxAge,
            Pageable pageable);


}