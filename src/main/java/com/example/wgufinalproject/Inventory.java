package com.example.wgufinalproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Random;
import java.util.regex.Pattern;
/** Static class for containing and interfacing with Parts and Products. */
public class Inventory {
    public static ObservableList<Part> allParts = FXCollections.observableArrayList();

    public static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part addPart){
        allParts.add(addPart);
    }

    public static void addProduct(Product product){
        allProducts.add(product);
    }

    /** Searches and returns Part by ID if found - otherwise returns null.
     @param partId Integer parameter used to search inventory.
     @return part Part containing matching ID.
     */
    public static Part lookupPart(int partId){
        ObservableList<Part> tempOL;
        tempOL = Inventory.getAllParts();
        for (Part part : tempOL) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    /** Searches and returns Product by ID if found - otherwise returns null.
     @param productId Integer parameter used to search inventory.
     @return product Product containing matching ID.
     */
    public static Product lookupProduct(int productId){
        ObservableList<Product> tempOL;
        tempOL = Inventory.getAllProducts();
        for (Product product : tempOL) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /** Searches and returns all Parts containing partName if found.
     @param partName String parameter used to search inventory.
     @return filteredList ObservableArrayList containing all matching Parts.
     */
    public static ObservableList lookupPart(String partName){
        ObservableList<Part> totalList = Inventory.getAllParts();
        ObservableList<Part> filteredList = FXCollections.observableArrayList();

        if (partName.chars().allMatch(Character::isDigit) && !partName.isEmpty()){
            try{
                Integer.parseInt(partName);
            }catch (NumberFormatException inputError) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setContentText("Please enter either a part ID (integer) or name (string)");
                alert.showAndWait();
            }
            for( Part part : totalList){
                if(Integer.toString(part.getId()).contains(partName))
                    filteredList.add(part);
            }
        }
        for(Part part : totalList){
            if(part.getName().toLowerCase().contains(partName.toLowerCase()))
                filteredList.add(part);
        }
        return filteredList;

    }

    /** Searches and returns all Products containing partName if found.
     @param productName String parameter used to search inventory.
     @return filteredList1 ObservableArrayList containing all matching Product.
     */

    public static ObservableList lookupProduct(String productName){
        ObservableList<Product> totalList = Inventory.getAllProducts();
        ObservableList<Product> filteredList1 = FXCollections.observableArrayList();

        if (productName.chars().allMatch(Character::isDigit) && !productName.isEmpty()){
            try{
                Integer.parseInt(productName);
                System.out.println("1");
            }catch (NumberFormatException inputError) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setContentText("Please enter either a product ID (integer) or name (string)");
                alert.showAndWait();
            }
            for( Product product : totalList){
                if(Integer.toString(product.getId()).contains(productName))
                    filteredList1.add(product);
            }
        }else {
            for (Product product : totalList) {
                if (product.getName().toLowerCase().contains(productName.toLowerCase())) {
                    System.out.println("3");
                    filteredList1.add(product);
                }
            }
        }
        return filteredList1;
    }

    /** Given the Part and its Id, this function searches and updates the Part in Inventory.
     RUNTIME ERROR - The function was seemingly not being called or doing nothing. Upon inspection,
     I was using the original Part "part" in the set method instead of the updated Part "selectedPart"
     @param id Integer parameter used to search inventory.
     @param selectedPart Part Updated Part to be inserted at appropriate location.
     */
    public static void updatePart(int id, Part selectedPart){
        System.out.println(selectedPart.getId());
        System.out.println((selectedPart.getName()));
        int loopIndex = -1;
        ObservableList<Part> tempOL;
        tempOL = Inventory.getAllParts();
        for (Part part : tempOL) {
            loopIndex++;
            if (part.getId() == id)
                Inventory.getAllParts().set(loopIndex, selectedPart);
        }
    }

    /** Given the Product and its Id, this function searches and updates the Product in Inventory.
     @param id Integer parameter used to search inventory.
     @param selectedProduct Product Updated Product to be inserted at appropriate location.
     */
    public static void updateProduct(int id, Product selectedProduct){
        System.out.println(selectedProduct.getId());
        System.out.println((selectedProduct.getName()));
        int loopIndex = -1;
        ObservableList<Product> tempOL;
        tempOL = Inventory.getAllProducts();
        for (Product product : tempOL) {
            loopIndex++;
            if (product.getId() == id)
                Inventory.getAllProducts().set(loopIndex, selectedProduct);
        }
    }

    /** This method removes the parameter Part from the Parts ObservableArrayList
     @param selectedPart Part to be removed from ObservableArrayList.
     @return success Upon successful removal, the function outputs boolean true
     */
    public static boolean deletePart(Part selectedPart){
        boolean success = true;
        if (!allParts.contains(selectedPart)) {
            success = false;
            return success;
        }
        allParts.remove(selectedPart);
        return success;
    }

    /** This method removes the parameter Product from the Products ObservableArrayList
     @param selectedProduct Product to be removed from ObservableArrayList.
     @return success Upon successful removal, the function outputs boolean true
     */
    public static boolean deleteProduct(Product selectedProduct){
        boolean success = true;
        if (!allProducts.contains(selectedProduct)) {
            success = false;
            return success;
        }
        allProducts.remove(selectedProduct);
        return success;
        }

    /** This method returns an ObservableArrayList containing all current Parts.
     @return allParts ObservableArrayList containing all current Parts.
     */
    public static ObservableList getAllParts(){
        return allParts;
    }

    /** This method returns an ObservableArrayList containing all current Products.
     @return allProducts ObservableArrayList containing all current Products.
     */
    public static ObservableList getAllProducts(){
        return allProducts;
    }

}
