package com.revature;

import com.revature.models.User;
import com.revature.models.UserRole;
import com.revature.repos.UserRepo;
import com.revature.services.UserService;
import com.revature.util.ConnectionFactory;

import java.io.IOException;

public class MainDriver {

    public static void main(String[] args) throws IOException {
//        System.out.println("Testing connection");
//        System.out.println(ConnectionFactory.getInstance().getConnection());
        UserRepo userRepo = new UserRepo();
        UserService userService = new UserService(userRepo);
       // register works - db saves id numbers through deletion?
        // User newUser = new User("CYanoso", "Revature", "Chris", "Yanoso", "CY@email.com", UserRole.EMPLOYEE);
        //userService.register(newUser);

        // authenticate works
        //System.out.println(userService.authenticate("CYanoso", "Revature"));

        // get user by ID works
        //System.out.println(userService.getUserById(3));

        // update user works
//        User updatedUser = new User(userService.getUserById(3).get());
//        System.out.println(updatedUser);
//        updatedUser.setEmail("NewEmail@email.com");
//        userService.update(updatedUser);

        //delete works
//        System.out.println(userService.getUserById(3));
//        System.out.println(userService.deleteUserById(3));
//        System.out.println(userService.getUserById(3));
    }
}
