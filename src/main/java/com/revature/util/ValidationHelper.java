package com.revature.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.User;
import com.revature.repos.UserRepo;
import com.revature.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

public class ValidationHelper {

    private final UserRepo userRepo= new UserRepo();
    private final UserService userService = new UserService(userRepo);

    public boolean process(HttpServletRequest req) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        switch (req.getRequestURI()) {
            case "/revabooks/email.validate":
                String email = mapper.readValue(req.getInputStream(), String.class);
                Optional<User> emailUser = userService.getUserByEmail(email);
                boolean emailValidate = (emailUser.isPresent());
                return emailValidate;

            case "/revabooks/username.validate":
                String username = mapper.readValue(req.getInputStream(), String.class);
                Optional<User> usernameUser = userService.getUserByUsername(username);
                boolean usernameValidate = (usernameUser.isPresent());
                return usernameValidate;

            default:
                return false;
        }

    }
}