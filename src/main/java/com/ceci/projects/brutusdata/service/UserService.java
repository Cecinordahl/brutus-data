package com.ceci.projects.brutusdata.service;

import com.ceci.projects.brutusdata.model.UserEntity;
import com.ceci.projects.brutusdata.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll().stream()
                // TODO remove comment. We use peek on the stream in getAllUsers to set the masked credit card number before returning users to the client.
                .peek(user -> user.setCcnumber(user.getMaskedCcnumber()))
                .collect(Collectors.toList());
    }

    public List<UserEntity> getUsers(int limit, int offset) {
        Pageable pageable = PageRequest.of(offset / limit, limit); // Calculates the page number
        return userRepository.findAll(pageable).getContent();
    }

    public List<UserEntity> searchUsers(String firstName, String lastName, String city, Integer minAge, Integer maxAge, int limit, int offset) {
        Pageable pageable = PageRequest.of(offset / limit, limit);
        return userRepository.findBySearchCriteria(firstName, lastName, city, minAge, maxAge, pageable);
    }

    public Optional<UserEntity> getUserById(Long id) {
        return userRepository.findById(id).map(user -> {
            // TODO remove comment.  apply getMaskedCcnumber() to only display the masked version in the response.
            user.setCcnumber(user.getMaskedCcnumber());
            return user;
        });
    }

    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

    public UserEntity updateUser(Long id, UserEntity updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            user.setAge(updatedUser.getAge());
            user.setStreet(updatedUser.getStreet());
            user.setCity(updatedUser.getCity());
            user.setState(updatedUser.getState());
            user.setLatitude(updatedUser.getLatitude());
            user.setLongitude(updatedUser.getLongitude());
            user.setCcnumber(updatedUser.getCcnumber());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
