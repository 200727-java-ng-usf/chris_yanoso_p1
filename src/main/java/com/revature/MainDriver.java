package com.revature;

import com.revature.models.*;
import com.revature.repos.ReimbursementRepo;
import com.revature.repos.UserRepo;
import com.revature.services.ReimbursementService;
import com.revature.services.UserService;
import com.revature.util.ConnectionFactory;

import java.io.IOException;
import java.sql.Timestamp;

public class MainDriver {

    public static void main(String[] args) throws IOException {
//        System.out.println("Testing connection");
//        System.out.println(ConnectionFactory.getInstance().getConnection());
        UserRepo userRepo = new UserRepo();
        UserService userService = new UserService(userRepo);
//       // register works - db saves id numbers through deletion?
//         User newUser = new User("CYanoso", "Revature", "Chris", "Yanoso", "CY@email.com", UserRole.EMPLOYEE);
//        userService.register(newUser);

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

//        ReimbursementRepo reimbursementRepo = new ReimbursementRepo();
//        ReimbursementService reimbursementService = new ReimbursementService(reimbursementRepo);
//        // added a new ticket successfully
//        double amount = 10.0;
//        Timestamp submitted = new Timestamp(System.currentTimeMillis());
//        String description = "Company Lunch";
//        String receipt = "insert url link here";
//        int authorId = 5;
//         ReimbursementStatus reimbursementStatus = ReimbursementStatus.PENDING;
//        ReimbursementType reimbursementType = ReimbursementType.FOOD;
//        Reimbursement reimbursement = new Reimbursement(amount, submitted, description, receipt, authorId, reimbursementStatus, reimbursementType);
//        reimbursementService.createNewReimbursement(reimbursement);
//        System.out.println(reimbursement);

//        gets all pending with id 5
//        System.out.println(reimbursementService.getPendingReimbursementById(userService.getUserById(5).get()));
        // gets all pending
        //  System.out.println(reimbursementService.getAllPendingReimbursements());

        //approve/deny works
//        Reimbursement first = reimbursementService.getReimbursementById(1);
//        System.out.println(first);
//        reimbursementService.approveReimbursement(first);
//        System.out.println(first);
//        Reimbursement second = reimbursementService.getReimbursementById(2);
//        System.out.println(second);
//        reimbursementService.denyReimbursement(second);
//        System.out.println(second);

//        //get all resolved works
//        //System.out.println(reimbursementService.getAllResolvedReimbursements());

        //get resolved by id works
//        Reimbursement second = reimbursementService.getReimbursementById(2);
//        second.setReimbursementStatus(ReimbursementStatus.PENDING);
//        reimbursementRepo.updateReimbursement(second);
//        User testUser = userService.getUserById(5).get();
//        System.out.println(reimbursementService.getResolvedReimbursementById(testUser));

        //new user methods, get all, get by username, get by email works
//        System.out.println(userService.getAllUsers());
//        System.out.println(userService.getUserByUsername("CYanoso"));
//        System.out.println(userService.getUserByEmail("CY@email.com"));
//        Reimbursement reimbursement = reimbursementService.getReimbursementById(1);
//        System.out.println(reimbursement.getSubmitted());

    }
}
