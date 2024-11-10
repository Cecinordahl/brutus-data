package com.ceci.projects.brutusdata.resource;

import com.ceci.projects.brutusdata.domain.UserEntity;
import com.ceci.projects.brutusdata.model.UserDto;
import com.ceci.projects.brutusdata.service.UserMapper;
import com.ceci.projects.brutusdata.service.UserService;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
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
    private final UserMapper userMapper;
    private final AopAutoConfiguration aopAutoConfiguration;

    public UserResource(UserService userService,
                        UserMapper userMapper, AopAutoConfiguration aopAutoConfiguration) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.aopAutoConfiguration = aopAutoConfiguration;
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
        int totalCount = userService.countUsers(firstName, lastName, city, minAge, maxAge);
        List<UserDto> userDtos = userMapper.toUserDtoList(users);

        return createStringObjectMap(userDtos, totalCount);
    }

    @GetMapping("/stats")
    public Map<String, Object> getUserStatistics() {
        return userService.findUserStatistics();
    }

    @PostMapping("/users")
    public UserDto createUser(@RequestBody UserEntity user) {
        UserEntity userEntity = userService.saveUser(user);
        return userMapper.toUserDto(userEntity);
    }

    @PostMapping("/users/upload")
    public List<UserDto> uploadUsersCsv(@RequestParam("file") MultipartFile file) {
        List<UserEntity> userEntities = userService.saveUsersFromCsv(file);
        return userMapper.toUserDtoList(userEntities);
    }

    /* Helper methods */

    private Map<String, Object> createStringObjectMap(List<UserDto> users, int totalCount) {
        Map<String, Object> response = new HashMap<>();
        response.put("data", users);
        response.put("total", totalCount);
        return response;
    }
}
