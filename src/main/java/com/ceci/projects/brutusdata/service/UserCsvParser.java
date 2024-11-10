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

    public List<UserEntity> parse(Resource resource) throws Exception {
        List<UserEntity> users = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(new InputStreamReader(resource.getInputStream()))) {
            csvReader.readNext(); // Skip header

            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                try {
                    users.add(parseRecord(nextRecord));
                } catch (IllegalArgumentException e) {
                    System.err.println("Skipping invalid record: " + e.getMessage());
                }
            }
        }

        return users;
    }

    private UserEntity parseRecord(String[] fields) {
        if (fields.length != 9) {
            throw new IllegalArgumentException("Record does not have the required 9 fields.");
        }

        UserEntity user = new UserEntity();
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
        if (!ccnumber.matches("\\d{15,16}")) {
            throw new IllegalArgumentException("Invalid credit card number");
        }
        return ccnumber;
    }
}
