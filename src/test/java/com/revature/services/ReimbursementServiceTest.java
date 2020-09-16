package com.revature.services;


import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.models.*;
import com.revature.repos.ReimbursementRepo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;
import java.sql.Timestamp;
import java.util.Optional;

public class ReimbursementServiceTest {
    private ReimbursementService reimbursementService;
    private ReimbursementRepo reimbursementRepo = Mockito.mock(ReimbursementRepo.class);
    private Reimbursement testReimbursement;
    private Reimbursement nullReimbursement;
    private User testUser;

    @Before
    public void setUp(){
        reimbursementService = new ReimbursementService(reimbursementRepo);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        testReimbursement = new Reimbursement(20, timestamp, "description", "receipt", 5, ReimbursementStatus.PENDING, ReimbursementType.FOOD);
        nullReimbursement = new Reimbursement(0, null, null, null, 0, null, null);
        testUser = new User(5, "test", "test", "test", "test", "test", UserRole.EMPLOYEE);
    }

    @Rule
    public ExpectedException execeptionRule = ExpectedException.none();

    @Test
    public void createNewReimbursementInvalid(){
        execeptionRule.expect(InvalidRequestException.class);
        execeptionRule.expectMessage("Invalid Reimbursement field values provided during ticket creation!");
        reimbursementService.createNewReimbursement(nullReimbursement);
    }

    @Test
    public void createNewReimbursementTrue(){
        reimbursementService.createNewReimbursement(testReimbursement);
    }

    @Test
    public void getReimbursementByIdInvalid(){
        execeptionRule.expect(InvalidRequestException.class);
        execeptionRule.expectMessage("Invalid id number");
        reimbursementService.getReimbursementById(0);
    }

    @Test
    public void getReimbursementByIdTrue(){
        Mockito.when(reimbursementRepo.getReimbursementById(1)).thenReturn(testReimbursement);
        Reimbursement actualResult = reimbursementService.getReimbursementById(1);
        Assert.assertEquals(testReimbursement, actualResult);
    }

    @Test
    public void getPendingReimbursementByUserIdNotFound() {
        Set<Reimbursement> emptySet = new HashSet<>();
        int userId = testUser.getId();
        Mockito.when(reimbursementRepo.getPendingReimbursementById(5)).thenReturn(emptySet);
        execeptionRule.expect(ResourceNotFoundException.class);
        execeptionRule.expectMessage("Employee number: " + userId + " has no pending reimbursement tickets");
        reimbursementService.getPendingReimbursementByUserId(testUser.getId());
    }

    @Test
    public void getPendingReimbursementByUserIdTrue(){
        Set<Reimbursement> testSet = new HashSet<>();
        testSet.add(testReimbursement);
        Mockito.when(reimbursementRepo.getPendingReimbursementById(5)).thenReturn(testSet);
        Set<Reimbursement> actualSet = reimbursementService.getPendingReimbursementByUserId(testUser.getId());
        Assert.assertEquals(testSet,actualSet);

    }

    @Test
    public void getResolvedReimbursementByUserIdNotFound() {
        Set<Reimbursement> emptySet = new HashSet<>();
        int userId = testUser.getId();
        Mockito.when(reimbursementRepo.getResolvedReimbursementById(5)).thenReturn(emptySet);
        execeptionRule.expect(ResourceNotFoundException.class);
        execeptionRule.expectMessage("Employee number: " + userId + " has no resolved reimbursement tickets");
        reimbursementService.getResolvedReimbursementByUserId(testUser.getId());
    }

    @Test
    public void getResolvedReimbursementByUserIdTrue() {
        Set<Reimbursement> testSet = new HashSet<>();
        testSet.add(testReimbursement);
        Mockito.when(reimbursementRepo.getResolvedReimbursementById(5)).thenReturn(testSet);
        Set<Reimbursement> actualSet = reimbursementService.getResolvedReimbursementByUserId(testUser.getId());
        Assert.assertEquals(testSet,actualSet);
    }

//    @Test
//    public void getAllPendingReimbursementsNotFound(){
//        Set<Reimbursement> emptySet = new HashSet<>();
//        Mockito.when(reimbursementRepo.getAllPendingReimbursements()).thenReturn(emptySet);
//        execeptionRule.expect(ResourceNotFoundException.class);
//        execeptionRule.expectMessage("There are no current Pending Reimbursements");
//        reimbursementService.getAllPendingReimbursements();
    //}

    @Test
    public void getAllPendingReimbursementsTrue(){
        Set<Reimbursement> testSet = new HashSet<>();
        testSet.add(testReimbursement);
        Mockito.when(reimbursementRepo.getAllPendingReimbursements()).thenReturn(testSet);
        Set<Reimbursement> actualSet = reimbursementService.getAllPendingReimbursements();
        Assert.assertEquals(testSet, actualSet);
    }

//    @Test
//    public void getAllResolvedReimbursementsNotFound(){
//        Set<Reimbursement> emptySet = new HashSet<>();
//        Mockito.when(reimbursementRepo.getAllResolvedReimbursements()).thenReturn(emptySet);
//        execeptionRule.expect(ResourceNotFoundException.class);
//        execeptionRule.expectMessage("There are no current Resolved Reimbursements");
//        reimbursementService.getAllResolvedReimbursements();
//    }

    @Test
    public void getAllResolvedReimbursementsTrue(){
        Set<Reimbursement> testSet = new HashSet<>();
        testSet.add(testReimbursement);
        Mockito.when(reimbursementRepo.getAllResolvedReimbursements()).thenReturn(testSet);
        Set<Reimbursement> actualSet = reimbursementService.getAllResolvedReimbursements();
        Assert.assertEquals(testSet, actualSet);
    }

    @Test
    public void approveReimbursementsTrue(){
        reimbursementService.approveReimbursement(testReimbursement);
    }

    @Test
    public void denyReimbursementsTrue(){
        reimbursementService.denyReimbursement(testReimbursement);
    }

    @Test
    public void updateReimbursementTrue(){
        reimbursementService.updateReimbursement(testReimbursement);
    }

}
