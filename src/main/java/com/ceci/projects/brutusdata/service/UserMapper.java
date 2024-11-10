package com.ceci.projects.brutusdata.service;

import com.ceci.projects.brutusdata.domain.UserEntity;
import com.ceci.projects.brutusdata.model.UserDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public List<UserDto> toUserDtoList(List<UserEntity> userEntityList) {
        if (userEntityList == null || userEntityList.isEmpty()) return null;

        return userEntityList.stream()
                .map(this::toUserDto)
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public UserDto toUserDto(UserEntity userEntity) {
        if (userEntity == null) return null;

        return new UserDto.Builder()
                .id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .age(userEntity.getAge())
                .street(userEntity.getStreet())
                .city(userEntity.getCity())
                .state(userEntity.getState())
                .latitude(userEntity.getLatitude())
                .longitude(userEntity.getLongitude())
                .ccnumber(userEntity.getCcnumber())
                .build();
    }
}
