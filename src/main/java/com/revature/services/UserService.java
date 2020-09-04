package com.revature.services;

import com.revature.exceptions.AuthenticationException;
import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.ResourcePersistenceException;
import com.revature.models.User;
import com.revature.repos.UserRepo;

import java.io.IOException;
import java.util.Optional;

public class UserService {

    private UserRepo userRepo;

    public UserService(UserRepo userRepo){this.userRepo = userRepo;}

    //used to login
    public Optional<User> authenticate(String username, String password) throws IOException {

        if (username == null || username.trim().equals("") || password == null || password.trim().equals("")) {
            throw new InvalidRequestException("Invalid username/password provided");
        }

        Optional<User> authenticatedUser =userRepo.findUserByCredentials(username, password);
        // mock userrepo
        // when/then
        // Mockito.when(userRepo.findUserByCredentials(Mockito.anyString(), Mockito.anyString())
        //        .thenReturn(Optional.empty());

        // Mockito.when(userRepo.findUserByCredentials(Mockito.anyString(), Mockito.anyString())
        //        .thenReturn(Optional.of(new AppUser());

        if (!authenticatedUser.isPresent()) {
            throw new AuthenticationException("No user found with the provided credentials");

        }

        return authenticatedUser;
    }


    //used to register
    public void register(User newUser) throws IOException {

        if (!isUserValid(newUser)) {
            throw new InvalidRequestException("Invalid user field values provided during registration!");
        }

        if (userRepo.findUserByUsername(newUser.getUsername()).isPresent()) {
            throw new ResourcePersistenceException("Provided username is already in use!");
        }
        if (userRepo.findUserByEmail(newUser.getEmail()).isPresent()){
            throw new ResourcePersistenceException("Provided email is already in use!");
        }

        userRepo.save(newUser);
    }


    //used to find user by id
    public Optional<User> getUserById(int id){

        if (id < 1){
            throw new InvalidRequestException("Invalid id number");
        }

        Optional<User> user = userRepo.findUserById(id);

        if (!user.isPresent()){
            throw new AuthenticationException("No user found with the provided id");
        }
        return user;

    }
    //deletes user by finding user by id
    public boolean deleteUserById(int id){
        Optional<User> user = userRepo.findUserById(id);
        userRepo.delete(user);
        //checks to see if user exists still
        if (userRepo.findUserByUsername(user.get().getUsername()).isPresent()){
            return false;
        } else {
            return true;
        }
    }


    //update user
    public void update (User updatedUser) throws IOException {
        userRepo.updateUser(updatedUser);
    }

    public boolean isUserValid(User user) {
        if (user == null) return false;
        if (user.getUsername() == null || user.getUsername().trim().equals("")) return false;
        if (user.getPassword() == null || user.getPassword().trim().equals("")) return false;
        if (user.getFirstName() == null || user.getFirstName().trim().equals("")) return false;
        if (user.getLastName() == null || user.getLastName().trim().equals("")) return false;
        if (user.getEmail() == null || user.getEmail().trim().equals("")) return false;
        return true;
    }
}
