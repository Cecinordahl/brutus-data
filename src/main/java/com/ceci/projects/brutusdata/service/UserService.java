package com.ceci.projects.brutusdata.service;

import com.ceci.projects.brutusdata.model.UserEntity;
import com.ceci.projects.brutusdata.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CsvService csvService;
    private final StatisticsService statisticsService;

    public UserService(UserRepository userRepository,
                       CsvService csvService,
                       StatisticsService statisticsService) {
        this.userRepository = userRepository;
        this.csvService = csvService;
        this.statisticsService = statisticsService;
    }

    public List<UserEntity> searchUsers(String firstName, String lastName, String city, Integer minAge, Integer maxAge, int limit, int offset) {
        Pageable pageable = PageRequest.of(offset / limit, limit);
        return userRepository.findBySearchCriteria(firstName, lastName, city, minAge, maxAge, pageable);
    }

    public int countUsers(String firstName, String lastName, String city, int minAge, int maxAge) {
        return userRepository.countBySearchCriteria(firstName, lastName, city, minAge, maxAge);
    }

    public Map<String, Object> findUserStatistics() {
        List<UserEntity> users = userRepository.findAll();
        return statisticsService.findUserStatistics(users);
    }

    public UserEntity saveUser(UserEntity user) {
        return userRepository.save(user);
    }

    public List<UserEntity> saveUsersFromCsv(MultipartFile file) {
        List<UserEntity> users = csvService.parseCsvFile(file);
        return userRepository.saveAll(users);
    }
}
