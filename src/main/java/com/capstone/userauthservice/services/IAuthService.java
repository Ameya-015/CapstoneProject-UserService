package com.capstone.userauthservice.services;

import com.capstone.userauthservice.models.Token;
import com.capstone.userauthservice.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.antlr.v4.runtime.misc.Pair;

public interface IAuthService {
    public User signup(String name,
                       String email,
                       String password,
                       String phoneNumber) throws JsonProcessingException;

    Token login(String email, String password);

    User validateToken(String tokenValue);
}
