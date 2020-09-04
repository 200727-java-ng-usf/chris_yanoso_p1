package com.revature.util;

import com.revature.models.User;

import java.util.Optional;

public class CurrentUser {
    public static Optional<User> currentUser;

    public CurrentUser(){ super();}

    public static Optional<User> getCurrentUser() { return currentUser;}

    public static void setCurrentUser(Optional<User> currentUser) { CurrentUser.currentUser = currentUser; }
}
