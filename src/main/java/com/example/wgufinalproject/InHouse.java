package com.example.wgufinalproject;
/** Child class of Part that implements its fields and methods. */
public class InHouse extends Part {
    int machineId;

    /** Returns InHouse Machine ID as integer.
     Machine ID is unique to InHouse objects.
     @return machineId Returns machineId as integer */
    public int getMachineId() {
        return machineId;
    }

    /** Sets InHouse Machine ID to Integer parameter.
     Machine ID is unique to InHouse objects.
     @param machineId Integer parameter to assign as machineId */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /** InHouse constructor - used for each object instantiation.
     Implements Part constructor and also assigning unique field machineId
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }
}

