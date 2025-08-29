package com.capstone.userauthservice.dtos;

import com.capstone.userauthservice.models.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserDto {
    private long id;
    private String name;
    private String email;
    List<Role> roles = new ArrayList<>();
}
