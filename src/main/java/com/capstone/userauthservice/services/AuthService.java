package com.capstone.userauthservice.services;

import com.capstone.userauthservice.exceptions.PasswordMismatchException;
import com.capstone.userauthservice.exceptions.UserAlreadyExistsExceptions;
import com.capstone.userauthservice.exceptions.UserNotSignedException;
import com.capstone.userauthservice.models.User;
import com.capstone.userauthservice.repositories.UserRepository;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService implements IAuthService {

    private UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User signup(String name,
        String email,
        String password,
        String phoneNumber) {

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isPresent()) {
            throw new UserAlreadyExistsExceptions("User already exists. Try a different email");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhoneNumber(phoneNumber);

        return userRepository.save(user);
    }

    public Pair<User, String> login(String email, String password) {

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()) {
            throw new UserNotSignedException("Invalid email id. Enter a correct email id");
        }

        String storedPassword = optionalUser.get().getPassword();
        if(!password.equals(storedPassword)) {
            throw new PasswordMismatchException("Incorrect password. Enter the correct password");
        }

        return new Pair<>(optionalUser.get(), "token");
    }

}
