package com.capstone.userauthservice.controllers;

import com.capstone.userauthservice.dtos.LoginRequestDto;
import com.capstone.userauthservice.dtos.SignUpRequestDto;
import com.capstone.userauthservice.dtos.UserDto;
import com.capstone.userauthservice.models.User;
import com.capstone.userauthservice.services.IAuthService;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public UserDto signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        User user = authService.signup(signUpRequestDto.getName(), signUpRequestDto.getEmail(),
                signUpRequestDto.getPassword(), signUpRequestDto.getPhoneNumber());
        return from(user);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        Pair<User, String> pair = authService.login(loginRequestDto.getEmail(),
                loginRequestDto.getPassword());
        UserDto userDto = from(pair.a);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    private UserDto from(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());

        return userDto;
    }
}
