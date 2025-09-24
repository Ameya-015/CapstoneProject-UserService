package com.capstone.userauthservice.controllers;

import com.capstone.userauthservice.dtos.LoginRequestDto;
import com.capstone.userauthservice.dtos.SignUpRequestDto;
import com.capstone.userauthservice.dtos.UserDto;
import com.capstone.userauthservice.models.Token;
import com.capstone.userauthservice.models.User;
import com.capstone.userauthservice.services.IAuthService;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto) {
        Token token = authService.login(loginRequestDto.getEmail(),
                loginRequestDto.getPassword());
        return new ResponseEntity<>(token.getValue(), HttpStatus.OK);
    }

    @GetMapping("/validate/{tokenValue}")
    public UserDto validateToken(@PathVariable String tokenValue) {
        User user = authService.validateToken(tokenValue);
        return from(user);
    }

    private UserDto from(User user) {

        if(user == null) {
            return null;
        }

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());

        return userDto;
    }
}
