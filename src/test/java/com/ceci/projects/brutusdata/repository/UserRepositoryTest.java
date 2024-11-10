package com.ceci.projects.brutusdata.repository;

import com.ceci.projects.brutusdata.domain.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled // TODO fix
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        UserEntity user1 = new UserEntity();
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setCity("New York");
        user1.setAge(30);
        userRepository.save(user1);

        UserEntity user2 = new UserEntity();
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setCity("Los Angeles");
        user2.setAge(25);
        userRepository.save(user2);

        UserEntity user3 = new UserEntity();
        user3.setFirstName("John");
        user3.setLastName("Smith");
        user3.setCity("New York");
        user3.setAge(40);
        userRepository.save(user3);
    }

    @Test
    public void findBySearchCriteria_firstNameOnly() {
        Pageable pageable = PageRequest.of(0, 10);
        List<UserEntity> results = userRepository.findBySearchCriteria("John", null, null, 18, 100, pageable);

        assertThat(results).hasSize(2);
        assertThat(results).extracting(UserEntity::getFirstName).containsOnly("John");
    }

    @Test
    public void findBySearchCriteria_firstNameLowercaseOnly() {
        Pageable pageable = PageRequest.of(0, 10);
        List<UserEntity> results = userRepository.findBySearchCriteria("john", null, null, 18, 100, pageable);

        assertThat(results).hasSize(2);
        assertThat(results).extracting(UserEntity::getFirstName).containsOnly("John");
    }

    @Test
    public void findBySearchCriteria_cityOnly() {
        Pageable pageable = PageRequest.of(0, 10);
        List<UserEntity> results = userRepository.findBySearchCriteria(null, null, "New York", 18, 100, pageable);

        assertThat(results).hasSize(2);
        assertThat(results).extracting(UserEntity::getCity).containsOnly("New York");
    }

    @Test
    public void findBySearchCriteria_firstNameAndCity() {
        Pageable pageable = PageRequest.of(0, 10);
        List<UserEntity> results = userRepository.findBySearchCriteria("John", null, "New York", 18, 100, pageable);

        assertThat(results).hasSize(2);
        assertThat(results).extracting(UserEntity::getFirstName).containsOnly("John");
        assertThat(results).extracting(UserEntity::getCity).containsOnly("New York");
    }

    @Test
    public void findBySearchCriteria_withAgeRange() {
        Pageable pageable = PageRequest.of(0, 10);
        List<UserEntity> results = userRepository.findBySearchCriteria(null, null, null, 20, 35, pageable);

        assertThat(results).hasSize(2);
        assertThat(results).extracting(UserEntity::getAge).containsOnly(30, 25);
    }

    @Test
    public void findBySearchCriteria_withAgeRangeNoResults() {
        Pageable pageable = PageRequest.of(0, 10);
        List<UserEntity> results = userRepository.findBySearchCriteria(null, null, null, 50, 60, pageable);

        assertThat(results).isEmpty();
    }

    @Test
    public void findBySearchCriteria_noMatches() {
        Pageable pageable = PageRequest.of(0, 10);
        List<UserEntity> results = userRepository.findBySearchCriteria("Nonexistent", null, null, 18, 100, pageable);

        assertThat(results).isEmpty();
    }
}