package com.ceci.projects.brutusdata.config;

import com.ceci.projects.brutusdata.domain.UserEntity;
import com.ceci.projects.brutusdata.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CsvDataLoaderTest {

    private static final String LEGACY_DATA_CSV = "classpath:data/legacy-user-data.csv";

    @Mock
    private UserRepository userRepository;

    @Mock
    private ResourceLoader resourceLoader;

    @InjectMocks
    private CsvDataLoader csvDataLoader;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testValidCsvDataLoadsSuccessfully() throws Exception {
        // Mock the UserRepository count to simulate an empty database
        when(userRepository.count()).thenReturn(0L);

        // Create a mock CSV resource
        Resource validResource = new ClassPathResource("data/valid_data.csv");
        when(resourceLoader.getResource(LEGACY_DATA_CSV)).thenReturn(validResource);

        // Run the data loader
        csvDataLoader.run();

        // Verify that save was called twice, once for each valid user
        verify(userRepository, times(2)).save(any(UserEntity.class));
    }

    @Test
    void testInvalidCsvDataIsSkipped() throws Exception {
        when(userRepository.count()).thenReturn(0L);

        // Load the invalid CSV file as a resource
        Resource invalidResource = new ClassPathResource("data/invalid_data.csv");
        when(resourceLoader.getResource(LEGACY_DATA_CSV)).thenReturn(invalidResource);

        // Run the data loader
        csvDataLoader.run();

        // Verify that no users were saved due to invalid data
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void testMixedValidAndInvalidData() throws Exception {
        when(userRepository.count()).thenReturn(0L);

        // Load the mixed CSV file as a resource
        Resource mixedResource = new ClassPathResource("data/mixed_data.csv");
        when(resourceLoader.getResource(LEGACY_DATA_CSV)).thenReturn(mixedResource);

        // Run the data loader
        csvDataLoader.run();

        // Verify that only one valid user was saved
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }
}
