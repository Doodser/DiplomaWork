package com.doodser.main.service;

import com.doodser.main.api.request.RegisterRequest;
import com.doodser.main.api.response.RegisterResponse;
import com.doodser.main.model.User;
import com.doodser.main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class RegisterService {
    @Autowired
    private UserRepository userRepository;

    public RegisterResponse register(RegisterRequest registerRequest) {
        String email = registerRequest.getEmail();
        String password = registerRequest.getPassword();
        String name = registerRequest.getName();

        RegisterResponse response = new RegisterResponse();
        if (userRepository.getByEmail(email) != null) {
            response.getErrors().put("email", "Этот email уже зарегистрирован");
        }
        if (name.isBlank()) {
            response.getErrors().put("name", "Имя указано неверно");
        }
        if (password.length() < 6) {
            response.getErrors().put("password", "Пароль короче 6-ти символв");
        }

        if (response.getErrors().isEmpty()) {
            User newUser = createUser(registerRequest);
            userRepository.save(newUser);

            response.setResult(true);
        }
        return response;
    }

    private User createUser(RegisterRequest registerRequest) {
        User user = new User();

        user.setEmail(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());
        user.setName(registerRequest.getName());
        user.setRegTime(new Date(new java.util.Date().getTime()));
        user.setIsModerator(0);
        user.setCode(null);
        user.setPhoto(null);

        return user;
    }
}
