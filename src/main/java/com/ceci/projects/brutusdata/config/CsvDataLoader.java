package com.ceci.projects.brutusdata.config;

import com.ceci.projects.brutusdata.domain.UserEntity;
import com.ceci.projects.brutusdata.repository.UserRepository;
import com.ceci.projects.brutusdata.service.UserCsvParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CsvDataLoader implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(CsvDataLoader.class);
    private static final String LEGACY_DATA_CSV = "classpath:data/legacy-user-data.csv";

    private final UserRepository userRepository;
    private final ResourceLoader resourceLoader;
    private final UserCsvParser userCsvParser;

    @Autowired
    public CsvDataLoader(UserRepository userRepository, ResourceLoader resourceLoader, UserCsvParser userCsvParser) {
        this.userRepository = userRepository;
        this.resourceLoader = resourceLoader;
        this.userCsvParser = userCsvParser;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            logger.info("Data already loaded; skipping CSV load.");
            return;
        }

        try {
            loadData();
        } catch (Exception e) {
            logger.error("Error loading CSV data", e);
        }
    }

    private void loadData() throws Exception {
        Resource resource = resourceLoader.getResource(LEGACY_DATA_CSV);
        List<UserEntity> users = userCsvParser.parse(resource);

        userRepository.saveAll(users);
        logger.info("CSV data loaded successfully: {} records loaded", users.size());
    }
}
