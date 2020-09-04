package com.revature.models;

public enum ReimbursementType {
    NA("Not Applicable"), LOGGING("Logging"), TRAVEL("Travel"), FOOD("Food"), OTHER("Other");
    private String typeName;
    ReimbursementType(String name){this.typeName = name;}

    public static ReimbursementType getByName(String name){

        for (ReimbursementType rt : ReimbursementType.values()){
            if (rt.typeName.equals(name)){
                return rt;
            }
        }
        return NA;
    }

    @Override
    public String toString(){return typeName;}
}
