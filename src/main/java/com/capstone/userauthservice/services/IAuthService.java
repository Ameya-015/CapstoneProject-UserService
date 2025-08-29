package com.capstone.userauthservice.services;

import com.capstone.userauthservice.models.User;
import org.antlr.v4.runtime.misc.Pair;

public interface IAuthService {
    public User signup(String name,
                       String email,
                       String password,
                       String phoneNumber);

    public Pair<User, String> login(String email, String password);
}
