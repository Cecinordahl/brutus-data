package com.ceci.projects.brutusdata.service;

import com.ceci.projects.brutusdata.model.UserEntity;
import com.ceci.projects.brutusdata.repository.UserRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> searchUsers(String firstName, String lastName, String city, Integer minAge, Integer maxAge, int limit, int offset) {
        Pageable pageable = PageRequest.of(offset / limit, limit);
        return userRepository.findBySearchCriteria(firstName, lastName, city, minAge, maxAge, pageable);
    }

    public int countUsers(String firstName, String lastName, String city, int minAge, int maxAge) {
        // Assuming you have a method in your repository to count users by these filters
        return userRepository.countBySearchCriteria(firstName, lastName, city, minAge, maxAge);
    }

    public Map<String, Object> getUserStatistics() {
        List<UserEntity> users = userRepository.findAll();

        // Total number of users
        long totalUsers = users.size();

        // Average age
        double averageAge = users.stream().mapToInt(UserEntity::getAge).average().orElse(0);

        // Age distribution (example: groups by decade)
        Map<String, Long> ageDistribution = users.stream()
                .collect(Collectors.groupingBy(user -> {
                    int age = user.getAge();
                    if (age < 20) return "Under 20";
                    else if (age <= 29) return "20-29";
                    else if (age <= 39) return "30-39";
                    else if (age <= 49) return "40-49";
                    else return "50+";
                }, Collectors.counting()));

        // Most common city
        String mostCommonCity = users.stream()
                .collect(Collectors.groupingBy(UserEntity::getCity, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Unknown");

        return Map.of(
                "totalUsers", totalUsers,
                "averageAge", averageAge,
                "ageDistribution", ageDistribution,
                "mostCommonCity", mostCommonCity
        );
    }

    public UserEntity saveUser(UserEntity user) {
        return userRepository.save(user);
    }

    public List<UserEntity> saveUsersFromCsv(MultipartFile file) {
        List<UserEntity> savedUsers = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            String[] values;
            int rowNumber = 0;

            while (true) {
                try {
                    values = csvReader.readNext();
                    if (values == null) break; // End of file

                    rowNumber++;
                    // Ensure each row has the required number of fields
                    if (values.length != 9) {
                        System.out.println("Skipping row " + rowNumber + ": Invalid number of fields");
                        continue;
                    }

                    // Map each field to a user entity
                    UserEntity user = mapToUserEntity(values);
                    savedUsers.add(userRepository.save(user));

                } catch (CsvValidationException e) {
                    System.out.println("Skipping row " + rowNumber + ": Invalid CSV format - " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to process the CSV file: " + e.getMessage());
        }

        return savedUsers;
    }

    private UserEntity mapToUserEntity(String[] values) {
        UserEntity user = new UserEntity();
        user.setFirstName(values[0].trim());
        user.setLastName(values[1].trim());
        user.setAge(Integer.parseInt(values[2].trim()));
        user.setStreet(values[3].trim());
        user.setCity(values[4].trim());
        user.setState(values[5].trim());
        user.setLatitude(Double.parseDouble(values[6].trim()));
        user.setLongitude(Double.parseDouble(values[7].trim()));
        user.setCcnumber(values[8].trim());
        return user;
    }
}
