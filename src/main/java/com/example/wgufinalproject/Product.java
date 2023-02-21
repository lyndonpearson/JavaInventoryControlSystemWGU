package com.example.wgufinalproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/** Child class of Inventory that implements product-related fields and methods of Inventory.
    Contains ObservableArrayList for maintaining part association for each Product object. */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /** Constructor called upon each Product instantiation */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /** Returns the Product's ID as an integer.
     @return Returns ID as integer
     */
    public int getId() {
        return id;
    }

    /** Sets Product's ID to integer parameter.
     @param id Integer parameter to assign as Product ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /** Returns the Product's Name as a String.
     @return Returns name as String
     */
    public String getName() {
        return name;
    }

    /** Sets Product's name to String parameter.
     @param name String parameter to assign as Product name
     */
    public void setName(String name) {
        this.name = name;
    }

    /** Returns the Product's Price as a Double.
     @return Returns price as Double
     */
    public double getPrice() {
        return price;
    }

    /** Sets Product's Price to Double parameter.
     @param price Double parameter to assign as Product price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /** Returns the Product's Stock as an Integer.
     @return Returns stock as Integer
     */
    public int getStock() {
        return stock;
    }

    /** Sets Product's Stock to Integer parameter.
     @param stock Integer parameter to assign as Product stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /** Returns the Product's Min as an Integer.
     @return Returns min as Integer
     */
    public int getMin() {
        return min;
    }

    /** Sets Product's Min to Integer parameter.
     @param min Integer parameter to assign as Product min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /** Returns the Product's Max as an Integer.
     @return Returns max as Integer
     */
    public int getMax() {
        return max;
    }

    /** Sets Product's Max to Integer parameter.
     @param max Integer parameter to assign as Product max
     */
    public void setMax(int max) {
        this.max = max;
    }

    /** Adds Part parameter to Product's associated parts list.
     @param part Part parameter to be associated with Product
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }
    /** Remove Part parameter to Product's associated parts list.
     @param part Part parameter to be removed from association with Product
     @return success Boolean output indicating success of operation.
     */
    public boolean deleteAssociatedPart(Part part){
            boolean success = false;
            if (associatedParts.contains(part)) {
                associatedParts.remove(part);
                success = true;
                return success;
            }
            return success;
    }

    /** Returns all associated Parts for the invoking Product.
     @return associatedParts ObservableArrayList containing associated Parts.
     */
    public ObservableList getAllAssociatedParts(){

        return associatedParts;
    }
}
