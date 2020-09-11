package com.revature.services;

import com.revature.exceptions.AuthenticationException;
import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.exceptions.ResourcePersistenceException;
import com.revature.models.User;
import com.revature.models.UserRole;
import com.revature.repos.UserRepo;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class UserServiceTest {
    private UserService userService;
    private UserRepo userRepo = Mockito.mock(UserRepo.class);
    private User testUser;
    private User nullUser;
    @Before
    public void setUp(){
        userService = new UserService(userRepo);
        testUser = new User("TestName", "TestPassword", "test", "test", "test", UserRole.EMPLOYEE);
        nullUser = new User( null, null, null, null, null, null);
    }

    @Rule
    public ExpectedException execeptionRule = ExpectedException.none();

    @Test
    public void authenticateException() throws IOException {
        String nullString = "";
        execeptionRule.expect(InvalidRequestException.class);
        execeptionRule.expectMessage("Invalid username/password provided");
        userService.authenticate(nullString, nullString);
    }

    @Test
    public void authenticateExceptionNull() throws IOException {
        String nullString = null;
        execeptionRule.expect(InvalidRequestException.class);
        execeptionRule.expectMessage("Invalid username/password provided");
        userService.authenticate(nullString, nullString);
    }

    @Test
    public void authenticateTrue() throws IOException {
        String username = "TestName";
        String password = "TestPassword";
        Mockito.when(userRepo.findUserByCredentials("TestName","TestPassword")).thenReturn(Optional.of(testUser));
        User actualUser = userService.authenticate(username,password);
        Assert.assertEquals(testUser,actualUser);
    }

    @Test
    public void authenticateNotFound() throws IOException {
        String username = "Test";
        String password = "Test";
        Mockito.when(userRepo.findUserByCredentials(username,password)).thenReturn(Optional.empty());
        execeptionRule.expect(AuthenticationException.class);
        execeptionRule.expectMessage("No user found with the provided credentials");
        userService.authenticate(username,password);
    }

    @Test
    public void registerInvalidRequest() throws IOException {
        execeptionRule.expect(InvalidRequestException.class);
        execeptionRule.expectMessage("Invalid user field values provided during registration!");
        userService.register(nullUser);
    }

    @Test
    public void registerFoundUsername() throws IOException {
        execeptionRule.expect(ResourcePersistenceException.class);
        execeptionRule.expectMessage("Provided username is already in use!");
        Mockito.when(userRepo.findUserByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));
        userService.register(testUser);
    }

    @Test
    public void registerFoundEmail() throws IOException {
        execeptionRule.expect(ResourcePersistenceException.class);
        execeptionRule.expectMessage("Provided email is already in use!");
        Mockito.when(userRepo.findUserByEmail(testUser.getEmail())).thenReturn(Optional.of(testUser));
        userService.register(testUser);
    }

    @Test
    public void registerTrue() throws IOException {
        userService.register(testUser);
    }

    @Test
    public void getUserByIdLessThanOneId() {
        execeptionRule.expect(InvalidRequestException.class);
        execeptionRule.expectMessage("Invalid id number");
        userService.getUserById(0);
    }

    @Test
    public void getUserByIdNotPresent() {
        execeptionRule.expect(ResourceNotFoundException.class);
        execeptionRule.expectMessage("No user found with the provided id");
        Mockito.when(userRepo.findUserById(5)).thenReturn(Optional.empty());
        userService.getUserById(5);
    }

    @Test
    public void getUserByIdTrue() {
        Mockito.when(userRepo.findUserById(5)).thenReturn(Optional.of(testUser));
        Optional<User> actualUser = userService.getUserById(5);
        Assert.assertEquals(testUser,actualUser.get());
    }

    @Test
    public void deleteUserByIdNoUserFound(){
        execeptionRule.expect(ResourceNotFoundException.class);
        execeptionRule.expectMessage("No user found with the provided id");
        Mockito.when(userRepo.findUserById(5)).thenReturn(Optional.empty());
        userService.deleteUserById(5);
    }

    @Test
    public void deleteUserByIdDidNotDelete() {
        Mockito.when(userRepo.findUserById(5)).thenReturn(Optional.of(testUser));
        Mockito.when(userRepo.findUserByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));
        boolean actualResult = userService.deleteUserById(5);
        boolean expectedResult = false;
        Assert.assertTrue(actualResult == expectedResult);
    }

    @Test
    public void deleteUserByIdTrue() {
        Mockito.when(userRepo.findUserById(5)).thenReturn(Optional.of(testUser));
        Mockito.when(userRepo.findUserByUsername(testUser.getUsername())).thenReturn(Optional.empty());
        boolean actualResult = userService.deleteUserById(5);
        boolean expectedResult = true;
        Assert.assertTrue(actualResult == expectedResult);
    }

    @Test
    public void getAllUsersNotFound() {
        Set<User> users = new HashSet<>();
        Mockito.when(userRepo.getAllUsers()).thenReturn(users);
        execeptionRule.expect(ResourceNotFoundException.class);
        execeptionRule.expectMessage("No users currently exist");
        userService.getAllUsers();
    }

    @Test
    public void getAllUsersTrue() {
        Set<User> users = new HashSet<>();
        users.add(testUser);
        Mockito.when(userRepo.getAllUsers()).thenReturn(users);
        Set<User> actualResult = userService.getAllUsers();
        Assert.assertEquals(users, actualResult);
    }

    @Test
    public void getUserByEmailTrue(){
        Mockito.when(userRepo.findUserByEmail(testUser.getEmail())).thenReturn(Optional.of(testUser));
        Optional<User> actualUser = userService.getUserByEmail(testUser.getEmail());
        Assert.assertEquals(testUser, actualUser.get());
    }

    @Test
    public void getUserByUsername(){
        Mockito.when(userRepo.findUserByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));
        Optional<User> actualUser = userService.getUserByUsername(testUser.getUsername());
        Assert.assertEquals(testUser, actualUser.get());
    }

    @Test
    public void updateTrue() throws IOException {
        userService.update(testUser);
    }
}
