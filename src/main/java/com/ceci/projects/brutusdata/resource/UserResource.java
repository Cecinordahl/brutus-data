package com.ceci.projects.brutusdata.resource;

import com.ceci.projects.brutusdata.model.UserEntity;
import com.ceci.projects.brutusdata.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/search")
    public Map<String, Object> searchUsers(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String city,
            @RequestParam(defaultValue = "16") int minAge,
            @RequestParam(defaultValue = "75") int maxAge,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int offset
    ) {
        List<UserEntity> users = userService.searchUsers(firstName, lastName, city, minAge, maxAge, limit, offset);
        int totalCount = userService.countUsers(firstName, lastName, city, minAge, maxAge); // New method to get total count

        Map<String, Object> response = new HashMap<>();
        response.put("data", users);
        response.put("total", totalCount); // Include total count in the response

        return response;
    }

    @GetMapping("/stats")
    public Map<String, Object> getUserStatistics() {
        return userService.getUserStatistics();
    }

    @PostMapping("/users")
    public UserEntity createUser(@RequestBody UserEntity user) {
        return userService.saveUser(user);
    }

    @PostMapping("/users/upload")
    public List<UserEntity> uploadUsersCsv(@RequestParam("file") MultipartFile file) {
        return userService.saveUsersFromCsv(file);
    }
}
