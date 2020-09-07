package com.revature.models;


import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;

public class Reimbursement {
    private int id;
    private double amount;
    private Timestamp submitted;
    private Timestamp resolved;
    private String description;
    private String receipt;
    private int authorId;
    private int resolverId;
    private ReimbursementStatus reimbursementStatus;
    private ReimbursementType reimbursementType;

    public Reimbursement() {
        super();
    }

    public Reimbursement(double amount, Timestamp submitted, String description, String receipt, int authorId, ReimbursementStatus reimbursementStatus, ReimbursementType reimbursementType) {
        this.amount = amount;
        this.submitted = submitted;
        this.description = description;
        this.receipt = receipt;
        this.authorId = authorId;
        this.reimbursementStatus = reimbursementStatus;
        this.reimbursementType = reimbursementType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Timestamp getSubmitted() {
        return submitted;
    }

    public void setSubmitted(Timestamp submitted) {
        this.submitted = submitted;
    }

    public Timestamp getResolved() {
        return resolved;
    }

    public void setResolved(Timestamp resolved) {
        this.resolved = resolved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getResolverId() {
        return resolverId;
    }

    public void setResolverId(int resolverId) {
        this.resolverId = resolverId;
    }

    public ReimbursementStatus getReimbursementStatus() {
        return reimbursementStatus;
    }

    public void setReimbursementStatus(ReimbursementStatus reimbursementStatus) {
        this.reimbursementStatus = reimbursementStatus;
    }

    public ReimbursementType getReimbursementType() {
        return reimbursementType;
    }

    public void setReimbursementType(ReimbursementType reimbursementType) {
        this.reimbursementType = reimbursementType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reimbursement reimbursement = (Reimbursement) o;
        return Objects.equals(id, reimbursement.id) &&
                Objects.equals(amount, reimbursement.amount) &&
                Objects.equals(submitted, reimbursement.submitted) &&
                Objects.equals(resolved, reimbursement.resolved) &&
                Objects.equals(description, reimbursement.description) &&
                Objects.equals(receipt, reimbursement.receipt) &&
                Objects.equals(authorId, reimbursement.authorId) &&
                Objects.equals(resolverId, reimbursement.resolverId) &&
                reimbursementStatus == reimbursement.reimbursementStatus &&
                reimbursementType == reimbursement.reimbursementType;
    }

    @Override
    public int hashCode() { return Objects.hash(id, amount, submitted, resolved, description, receipt, authorId, resolverId, reimbursementStatus, reimbursementType); }

    @Override
    public String toString() {
        return "Reimbursement{" +
                "id= " + id +
                ", amount= " + amount +
                ", submitted= " + submitted +
                ", resolved= " + resolved +
                ", description= '" + description + '\'' +
                ", receipt= '" + receipt + '\'' +
                ", authorId= " + authorId +
                ", resolverId= " + resolverId +
                ", reimbursementStatus= " + reimbursementStatus +
                ", reimbursementType= " + reimbursementType +
                '}';
    }
}
