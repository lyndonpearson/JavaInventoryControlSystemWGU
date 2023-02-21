package com.example.wgufinalproject;
/** Abstract parent class of InHouse and Outsourced classes. */
public abstract class Part {
    int id;
    String name;
    double price;
    int stock;
    int min;
    int max;

    /** Constructor whose fields and methods are implemented by child classes (InHouse and Outsource) */
    public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /** Returns the Part's ID as an integer.
     Implemented by child classes InHouse and Outsource.
     @return Returns ID as integer
     */
    public int getId() {
        return id;
    }

    /** Sets Part's ID to integer parameter.
     Implemented by child classes InHouse and Outsource.
     @param id Integer parameter to assign as Part ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /** Returns the Part's Name as a String.
     Implemented by child classes InHouse and Outsource.
     @return Returns name as String
     */
    public String getName() {
        return name;
    }

    /** Sets Part's name to String parameter.
     Implemented by child classes InHouse and Outsource.
     @param name String parameter to assign as Part name
     */
    public void setName(String name) {
        this.name = name;
    }

    /** Returns the Part's Price as a Double.
     Implemented by child classes InHouse and Outsource.
     @return Returns price as Double
     */
    public double getPrice() {
        return price;
    }

    /** Sets Part's Price to Double parameter.
     Implemented by child classes InHouse and Outsource.
     @param price Double parameter to assign as Part price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /** Returns the Part's Stock as an Integer.
     Implemented by child classes InHouse and Outsource.
     @return Returns stock as Integer
     */
    public int getStock() {
        return stock;
    }

    /** Sets Part's Stock to Integer parameter.
     Implemented by child classes InHouse and Outsource.
     @param stock Integer parameter to assign as Part stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /** Returns the Part's Min as an Integer.
     Implemented by child classes InHouse and Outsource.
     @return Returns min as Integer
     */
    public int getMin() {
        return min;
    }

    /** Sets Part's Min to Integer parameter.
     Implemented by child classes InHouse and Outsource.
     @param min Integer parameter to assign as Part min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /** Returns the Part's Max as an Integer.
     Implemented by child classes InHouse and Outsource.
     @return Returns max as Integer
     */
    public int getMax() {
        return max;
    }

    /** Sets Part's Max to Integer parameter.
     Implemented by child classes InHouse and Outsource.
     @param max Integer parameter to assign as Part max
     */
    public void setMax(int max) {
        this.max = max;
    }


}
