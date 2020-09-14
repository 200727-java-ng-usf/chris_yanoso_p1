package com.revature.services;

import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;
import com.revature.models.User;
import com.revature.repos.ReimbursementRepo;

import java.io.IOException;

import java.util.Set;

public class ReimbursementService {

    private ReimbursementRepo reimbursementRepo;

    public ReimbursementService(ReimbursementRepo reimbursementRepo){this.reimbursementRepo = reimbursementRepo;}

    public void createNewReimbursement(Reimbursement reimbursement) throws InvalidRequestException {
        if (!isReimbursementValid(reimbursement)){
            throw new InvalidRequestException("Invalid Reimbursement field values provided during ticket creation!");
        }

        reimbursementRepo.createNew(reimbursement);
    }

    public Reimbursement getReimbursementById (int id){
        if (id < 1){
            throw new InvalidRequestException("Invalid id number");
        }
        Reimbursement reimbursement = reimbursementRepo.getReimbursementById(id);
        return reimbursement;
    }
    public Set<Reimbursement> getPendingReimbursementByUserId (int userId){
        Set<Reimbursement> pending = reimbursementRepo.getPendingReimbursementById(userId);
        if (pending.isEmpty()){
            throw new ResourceNotFoundException("Employee number: " + userId + " has no pending reimbursement tickets");
        }
        return pending;
    }

    public Set<Reimbursement> getResolvedReimbursementByUserId (int userId){ ;
        Set<Reimbursement> resolved = reimbursementRepo.getResolvedReimbursementById(userId);
        if (resolved.isEmpty()){
            throw new ResourceNotFoundException("Employee number: " + userId + " has no resolved reimbursement tickets");
        }
        return resolved;
    }

    public Set<Reimbursement> getAllPendingReimbursements () {
        Set<Reimbursement> allPending = reimbursementRepo.getAllPendingReimbursements();
        if (allPending.isEmpty()){
            throw new ResourceNotFoundException("There are no current Pending Reimbursements");
        }
        return allPending;
    }

    public Set<Reimbursement> getAllResolvedReimbursements() {
        Set<Reimbursement> allResolved = reimbursementRepo.getAllResolvedReimbursements();
        if (allResolved.isEmpty()){
            throw new ResourceNotFoundException("There are no current Resolved Reimbursements");
        }
        return allResolved;
    }

    public void approveReimbursement(Reimbursement reimbursement){
        reimbursement.setReimbursementStatus(ReimbursementStatus.APPROVED);
        reimbursementRepo.updateReimbursement(reimbursement);
    }

    public void denyReimbursement(Reimbursement reimbursement){
        reimbursement.setReimbursementStatus(ReimbursementStatus.DENIED);
        reimbursementRepo.updateReimbursement(reimbursement);
    }

    public void updateReimbursement(Reimbursement reimbursement){
        reimbursementRepo.updateReimbursement(reimbursement);
    }

    private boolean isReimbursementValid(Reimbursement reimbursement) {
        if (reimbursement == null) return false;
        if (reimbursement.getAmount() == 0 || reimbursement.getAmount() < 0) return false;
        if (reimbursement.getDescription() == null || reimbursement.getDescription().trim().equals("")) return false;
        if (reimbursement.getReceipt() == null || reimbursement.getReceipt().trim().equals("")) return false;
        return true;
    }
}
