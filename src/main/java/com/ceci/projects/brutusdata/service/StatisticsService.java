package com.ceci.projects.brutusdata.service;

import org.springframework.stereotype.Service;
import com.ceci.projects.brutusdata.domain.UserEntity;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatisticsService {

    public Map<String, Object> findUserStatistics(List<UserEntity> users) {
        return Map.of(
                "totalUsers", users.size(),
                "averageAge", calculateAverageAge(users),
                "ageDistribution", calculateAgeDistribution(users),
                "mostCommonCity", findMostCommonCity(users)
        );
    }

    private double calculateAverageAge(List<UserEntity> users) {
        return users.stream()
                .mapToInt(UserEntity::getAge)
                .average()
                .orElse(0);
    }

    private Map<String, Long> calculateAgeDistribution(List<UserEntity> users) {
        return users.stream()
                .collect(Collectors.groupingBy(user -> {
                    int age = user.getAge();
                    if (age < 20) return "Under 20";
                    else if (age <= 29) return "20-29";
                    else if (age <= 39) return "30-39";
                    else if (age <= 49) return "40-49";
                    else return "50+";
                }, Collectors.counting()));
    }

    private String findMostCommonCity(List<UserEntity> users) {
        return users.stream()
                .collect(Collectors.groupingBy(UserEntity::getCity, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Unknown");
    }
}
