package com.capstone.userauthservice.services;

import com.capstone.userauthservice.exceptions.InvalidTokenException;
import com.capstone.userauthservice.exceptions.PasswordMismatchException;
import com.capstone.userauthservice.exceptions.UserAlreadyExistsExceptions;
import com.capstone.userauthservice.exceptions.UserNotSignedException;
import com.capstone.userauthservice.models.Token;
import com.capstone.userauthservice.models.User;
import com.capstone.userauthservice.repositories.TokenRepository;
import com.capstone.userauthservice.repositories.UserRepository;
import org.antlr.v4.runtime.misc.Pair;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthService implements IAuthService {

    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private TokenRepository tokenRepository;

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
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setPhoneNumber(phoneNumber);

        return userRepository.save(user);
    }

    public Token login(String email, String password) {

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()) {
            throw new UserNotSignedException("Invalid email id. Enter a correct email id");
        }

        if(!bCryptPasswordEncoder.matches(password, optionalUser.get().getPassword())) {
            throw new PasswordMismatchException("Incorrect password. Enter the correct password");
        }

        // Creating token
        Token token = new Token();
        token.setUser(optionalUser.get());
        token.setValue(RandomStringUtils.randomAlphanumeric(128));

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 30);
        Date after30Days = calendar.getTime();

        token.setExpiresAt(after30Days);

        tokenRepository.save(token);

        return token;
    }

    public User validateToken(String tokenValue) {
        Optional<Token> optionalToken = tokenRepository
                .findByValueAndExpiresAtAfter(tokenValue, new Date());
        if(optionalToken.isEmpty()) {
            throw new InvalidTokenException("Token is invalid");
        }
        return optionalToken.get().getUser();
    }

}
