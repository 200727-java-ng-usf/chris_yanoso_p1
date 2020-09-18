package com.revature.models;

public enum ReimbursementStatus {
    NA("Not Applicable"), APPROVED("Approved"), DENIED("Denied"), PENDING("Pending");
    private String statusName;
    ReimbursementStatus(String name){this.statusName = name;}

    public static ReimbursementStatus getByName(String name){

        for (ReimbursementStatus rs : ReimbursementStatus.values()){
            if (rs.statusName.equals(name)){
                return rs;
            }
        }
        return NA;
    }

    @Override
    public String toString(){return statusName;}
}
