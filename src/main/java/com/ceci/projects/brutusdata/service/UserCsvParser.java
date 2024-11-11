package com.ceci.projects.brutusdata.service;

import com.ceci.projects.brutusdata.domain.UserEntity;
import com.opencsv.CSVReader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserCsvParser {

    private static final String CREDIT_CARD_REGEX = "\\d{15,16}";
    private static final int VALID_AMOUNT_OF_FIELDS = 10;
    private static final int MIN_AGE = 0;
    private static final int MAX_AGE = 120;
    private static final int MAX_LATITUDE = 90;
    private static final int MIN_LATITUDE = -MAX_LATITUDE;
    private static final int MAX_LONGITUDE = 180;
    private static final int MIN_LONGITUDE = -MAX_LONGITUDE;

    public List<UserEntity> parse(Resource resource) throws Exception {
        List<UserEntity> users = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(new InputStreamReader(resource.getInputStream()))) {
            csvReader.readNext(); // Skip header

            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                try {
                    UserEntity user = parseRecord(nextRecord);
                    users.add(user);
                } catch (IllegalArgumentException e) {
                    System.err.println("Skipping invalid record: " + e.getMessage());
                }
            }
        }

        return users;
    }

    private UserEntity parseRecord(String[] fields) {
        if (fields.length != VALID_AMOUNT_OF_FIELDS) {
            throw new IllegalArgumentException("Record does not have the required 9 fields.");
        }

        UserEntity user = new UserEntity();
        // fields[0] is the "seq" field, which we ignore since we use auto incremented id instead
        user.setFirstName(cleanString(fields[1]));
        user.setLastName(cleanString(fields[2]));
        user.setAge(validateAge(fields[3]));
        user.setStreet(cleanString(fields[4]));
        user.setCity(cleanString(fields[5]));
        user.setState(cleanString(fields[6]));
        user.setLatitude(validateLatitude(fields[7]));
        user.setLongitude(validateLongitude(fields[8]));
        user.setCcnumber(validateCreditCardNumber(fields[9]));

        return user;
    }

    private String cleanString(String value) {
        return value != null ? value.trim() : "";
    }

    private int validateAge(String ageStr) {
        int age = Integer.parseInt(ageStr.trim());
        if (age < MIN_AGE || age > MAX_AGE) {
            throw new IllegalArgumentException("Invalid age: " + age);
        }
        return age;
    }

    private double validateLatitude(String latStr) {
        double latitude = Double.parseDouble(latStr.trim());
        if (latitude < MIN_LATITUDE || latitude > MAX_LATITUDE) {
            throw new IllegalArgumentException("Invalid latitude: " + latitude);
        }
        return latitude;
    }

    private double validateLongitude(String lonStr) {
        double longitude = Double.parseDouble(lonStr.trim());
        if (longitude < MIN_LONGITUDE || longitude > MAX_LONGITUDE) {
            throw new IllegalArgumentException("Invalid longitude: " + longitude);
        }
        return longitude;
    }

    private String validateCreditCardNumber(String ccStr) {
        if (ccStr == null || ccStr.isBlank()) {
            throw new IllegalArgumentException("Credit card number cannot be null or empty");
        }

        String ccNumber = ccStr.trim();

        if (!ccNumber.matches(CREDIT_CARD_REGEX)) {
            throw new IllegalArgumentException("Invalid credit card number format: must be 15-16 digits");
        }

        return ccNumber;
    }
}
