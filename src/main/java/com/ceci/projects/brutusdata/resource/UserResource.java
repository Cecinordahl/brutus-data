package com.ceci.projects.brutusdata.resource;

import com.ceci.projects.brutusdata.model.UserEntity;
import com.ceci.projects.brutusdata.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserEntity> getAllUsers(@RequestParam(defaultValue = "10") int limit,
                                        @RequestParam(defaultValue = "0") int offset
    ) {
        return userService.getUsers(limit, offset);
    }

    @GetMapping("/search")
    public List<UserEntity> searchUsers(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String city,
            @RequestParam(defaultValue = "16") int minAge,
            @RequestParam(defaultValue = "75") int maxAge,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int offset
    ) {
        return userService.searchUsers(firstName, lastName, city, minAge, maxAge, limit, offset);
    }

    @GetMapping("/{id}")
    public UserEntity getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PostMapping
    public UserEntity createUser(@RequestBody UserEntity user) {
        return userService.createUser(user);
    }

    @PatchMapping("/{id}")
    public UserEntity updateUser(@PathVariable Long id, @RequestBody UserEntity updatedUser) {
        return userService.updateUser(id, updatedUser);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
