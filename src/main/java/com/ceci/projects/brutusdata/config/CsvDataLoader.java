package com.ceci.projects.brutusdata.config;

import com.ceci.projects.brutusdata.model.UserEntity;
import com.ceci.projects.brutusdata.repository.UserRepository;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Objects;

@Component
public class CsvDataLoader implements CommandLineRunner {

    private static final String LEGACY_DATA_CSV = "classpath:data/legacy-user-data.csv";
    private final UserRepository userRepository;
    private final ResourceLoader resourceLoader;

    @Autowired
    public CsvDataLoader(UserRepository userRepository, ResourceLoader resourceLoader) {
        this.userRepository = userRepository;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            Resource resource = resourceLoader.getResource(LEGACY_DATA_CSV);

            try (Reader reader = new InputStreamReader(resource.getInputStream());
                 CSVReader csvReader = new CSVReader(reader)) {

                csvReader.readNext(); // Skip header

                String[] nextRecord;
                int recordsLoaded = 0;
                int recordsSkipped = 0;

                while ((nextRecord = csvReader.readNext()) != null) {
                    try {
                        // Validate and clean each field
                        String firstName = cleanString(nextRecord[1]);
                        String lastName = cleanString(nextRecord[2]);
                        int age = validateAge(nextRecord[3]);
                        String street = cleanString(nextRecord[4]);
                        String city = cleanString(nextRecord[5]);
                        String state = cleanString(nextRecord[6]);
                        double latitude = validateLatitude(nextRecord[7]);
                        double longitude = validateLongitude(nextRecord[8]);
                        String ccnumber = validateCreditCardNumber(nextRecord[9]);

                        // Create and save UserEntity
                        UserEntity user = new UserEntity();
                        user.setFirstName(firstName);
                        user.setLastName(lastName);
                        user.setAge(age);
                        user.setStreet(street);
                        user.setCity(city);
                        user.setState(state);
                        user.setLatitude(latitude);
                        user.setLongitude(longitude);
                        user.setCcnumber(ccnumber);

                        userRepository.save(user);
                        recordsLoaded++;
                    } catch (Exception e) {
                        // Log and skip invalid records
                        System.err.println("Skipping invalid record: " + e.getMessage());
                        recordsSkipped++;
                    }
                }

                System.out.println("CSV data loaded successfully: " + recordsLoaded + " records loaded, " + recordsSkipped + " records skipped.");
            } catch (Exception e) {
                System.err.println("Error loading CSV data: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Data already loaded; skipping CSV load.");
        }
    }

    // Helper methods for validation and cleaning

    private String cleanString(String value) {
        return value != null ? value.trim() : "";
    }

    private int validateAge(String ageStr) {
        int age = Integer.parseInt(ageStr.trim());
        if (age < 0 || age > 120) {
            throw new IllegalArgumentException("Invalid age: " + age);
        }
        return age;
    }

    private double validateLatitude(String latStr) {
        double latitude = Double.parseDouble(latStr.trim());
        if (latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException("Invalid latitude: " + latitude);
        }
        return latitude;
    }

    private double validateLongitude(String lonStr) {
        double longitude = Double.parseDouble(lonStr.trim());
        if (longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("Invalid longitude: " + longitude);
        }
        return longitude;
    }

    private String validateCreditCardNumber(String ccStr) {
        String ccnumber = ccStr.trim();
        if (!ccnumber.matches("\\d{13,19}")) { // Validate length and numeric format
            throw new IllegalArgumentException("Invalid credit card number");
        }
        return ccnumber;
    }
}
