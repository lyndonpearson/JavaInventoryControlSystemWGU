package com.example.wgufinalproject;
/** Child class of Part that implements its fields and methods. */
public class Outsourced extends Part{
    String companyName;

    /** Returns OutSource Company Name as String.
     Company Name is unique to OutSource objects.
     @return companyName Returns companyName as String */
    public String getCompanyName() {
        return companyName;
    }

    /** Sets OutSource Company Name to String parameter.
     Company Name is unique to OutSource objects.
     @param companyName String parameter to assign as companyName */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /** OutSource constructor - used for each object instantiation.
     Implements Part constructor and also assigning unique field companyName
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
}
