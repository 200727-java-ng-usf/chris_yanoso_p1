package com.revature.models;

public enum UserRole {
    NA("Not Applicable"), EMPLOYEE("Employee"), ADMIN("Admin"), MANAGER("Manager");
    private String roleName;
    UserRole(String name) {
        this.roleName = name;
    }

    public static UserRole getByName(String name){

        for (UserRole userRole : UserRole.values()){
            if (userRole.roleName.equals(name)){
                return userRole;
            }
        }
        return NA;
    }

    @Override
    public String toString() {
        return roleName;
    }
}
