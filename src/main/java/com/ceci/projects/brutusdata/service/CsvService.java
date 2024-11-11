package com.ceci.projects.brutusdata.service;

import com.ceci.projects.brutusdata.domain.UserEntity;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvService {

    private static final int VALID_AMOUNT_OF_FIELDS = 9;

    public List<UserEntity> parseCsvFile(MultipartFile file) {
        List<UserEntity> users = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            String[] values;
            int rowNumber = 0;

            while ((values = csvReader.readNext()) != null) {
                rowNumber++;
                if (values.length != VALID_AMOUNT_OF_FIELDS) {
                    System.out.println("Skipping row " + rowNumber + ": Invalid number of fields");
                    continue;
                }
                try {
                    users.add(mapToUserEntity(values));
                } catch (NumberFormatException e) {
                    System.out.println("Skipping row " + rowNumber + ": Error parsing numeric field - " + e.getMessage());
                }
            }
        } catch (IOException | CsvValidationException e) {
            System.out.println("Failed to process the CSV file: " + e.getMessage());
        }

        return users;
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