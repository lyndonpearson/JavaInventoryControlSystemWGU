package com.example.wgufinalproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

/** AddProductFormController class created with initialization capabilities.
 The controller interfaces with the text fields, TableViews,
 and buttons shown in AddProductForm.fxml
 FUTURE ENHANCEMENT - The TableViews could contain a column indicating whether the part
 was InHouse or Outsourced. This would further inform the user in case Outsourced parts
 have longer lead times for delivery
 */
public class AddProductFormController implements Initializable {
    Parent scene;
    Stage stage;
    @FXML
    private Button addPartBtn;

    @FXML
    private TableColumn<?, ?> asPartIdCol;

    @FXML
    private TableColumn<?, ?> asPartNameCol;

    @FXML
    private TableColumn<?, ?> asPartPriceCol;

    @FXML
    private TableColumn<?, ?> asPartStockCol;

    @FXML
    private TableView<Part> asPartsTableView;

    @FXML
    private Button cancelBtn;

    @FXML
    private TableColumn<?, ?> partIdCol;

    @FXML
    private TableColumn<?, ?> partNameCol;

    @FXML
    private TableColumn<?, ?> partPriceCol;

    @FXML
    private TableColumn<?, ?> partStockCol;

    @FXML
    private TableView<Part> partsAvailableTableView;

    @FXML
    private TextField productId;

    @FXML
    private TextField productMax;

    @FXML
    private TextField productMin;

    @FXML
    private TextField productName;

    @FXML
    private TextField productPrice;

    @FXML
    private TextField productSearch;

    @FXML
    private TextField productStock;

    @FXML
    private Button removeAsPartBtn;

    @FXML
    private Button saveBtn;
    /** This method is called if the Add
     button is selected. The selected Part in the top TableView is added to the
     bottom TableView, associating the Part with the Product.
     @param event The event of the Add button being clicked
     */
    @FXML
    void onAddBtnClick(ActionEvent event) {
        Part tempPart = partsAvailableTableView.getSelectionModel().getSelectedItem();
        asPartsTableView.getItems().add(tempPart);

        asPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        asPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        asPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        asPartStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }

    /** This method is called if the Cancel
     button is selected. The window is returned to the MainForm.fxml file
     and nothing else is done
     @param event The event of the Cancel button being clicked
     */
    @FXML
    void onCancelBtnClick(ActionEvent event) throws IOException {
        stage = (Stage)((Button) event.getSource()).getScene().getWindow();

        scene = FXMLLoader.load(getClass().getResource("MainForm.fxml"));

        stage.setScene(new Scene(scene));

        stage.show();
    }
    /** This method is called if the Remove Associated Part
     button is selected. The selected Part in the bottom TableView is removed
     from the bottom TableView, de-associating the Part with the Product.
     @param event The event of the Add button being clicked
     */
    @FXML
    void onRemoveAsPartBtnClick(ActionEvent event) throws IOException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will remove association," +
                "do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            Part selectedPart = asPartsTableView.getSelectionModel().getSelectedItem();
            asPartsTableView.getItems().remove(selectedPart);
        }
    }
    /** This method is called if the Save
     button is selected. The text fields are validated and, if all correct,
     the data is extracted to create a new Product, add it to inventory,
     then associate the parts present in bottom Tableview.
     The window is then returned to the MainForm.fxml file.
     @param event The event of the Save button being clicked
     */
    @FXML
    void onSaveBtnClick(ActionEvent event) throws IOException{
        int id = 0;
        String name;
        int inv = 0;
        double price;
        int max = 0;
        int min = 0;

        try{
            id = Integer.parseInt(productId.getText());
            name = productName.getText();
            inv = Integer.parseInt(productStock.getText());
            price = Double.parseDouble(productPrice.getText());
            max = Integer.parseInt(productMax.getText());
            min = Integer.parseInt(productMin.getText());

            if ((min >= max) || (inv <= min) || (inv >= max))
                throw new Exception();

            Product tempProduct = new Product(id, name, price, inv, min, max);
            Inventory.addProduct(tempProduct);

            ObservableList<Part> assParts = FXCollections.observableArrayList();
            assParts = asPartsTableView.getItems();
            for (Part part : assParts)
                tempProduct.addAssociatedPart(part);

            stage = (Stage)((Button) event.getSource()).getScene().getWindow();

            scene = FXMLLoader.load(getClass().getResource("MainForm.fxml"));

            stage.setScene(new Scene(scene));

            stage.show();

        } catch(NumberFormatException inputError){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please enter a valid value for each text field");
            alert.showAndWait();
        } catch (Exception numberError) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Error Max must be greater than Min and Inventory between them");
            alert.showAndWait();
        }
    }
    /** This method is called when the Search Field is selected.
     Any existing data present in the field is removed.
     RUNTIME ERROR - the text field wasn't being cleared properly or consistently.
     I referenced course material to find a more suitable event handler (OnMouseclick).
     @param event The event of the Search Field being selected.
     */
    @FXML
    void clearSearchField(MouseEvent event) {
        productSearch.clear();
    }

    /** This method is called anything is typed in the Search Field.
     If no ID or Product Name matches the entry, an error is displayed
     otherwise matches are displayed in the top TableView.
     If the Search field is cleared, the top TableView is re-populated
     with the original data.
     @param event The event when data is entered in the Search Field.
     */
    @FXML
    void searchFieldEntry(KeyEvent event) {
        ObservableList<Part> filteredListPart = FXCollections.observableArrayList();
        String testValue = productSearch.getText();

        filteredListPart = Inventory.lookupPart(testValue);

        if (filteredListPart.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product Error Dialog");
            alert.setContentText("Error No Results Found");
            alert.showAndWait();
        }else {
            partsAvailableTableView.setItems(filteredListPart);
            partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            partStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        }

        if (testValue.isEmpty())
            partsAvailableTableView.setItems(Inventory.getAllParts());
    }

    /** This method is called when the AddProductForm.fxml file is loaded.
     The ID text field is disabled and a unique one is assigned.
     The top TableView is populated with existing data from Inventory.
     @param url The location of the relative path of the root object.
     @param resourceBundle Resource used to localize the root object; can be null if absolute path.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productId.setDisable(true);

        ObservableList<Part> searchList = Inventory.getAllParts();
        boolean searchCondition = true;
        int nextId = 0;
        while (searchCondition) {
            Random rand = new Random();
            nextId = rand.nextInt(1000);
            boolean existId = false;

            for (Part part : searchList)
                if (part.getId() == nextId)
                    existId = true;
            if (!existId)
                searchCondition = false;
        }
        productId.setText(Integer.toString(nextId));

        partsAvailableTableView.setItems(Inventory.getAllParts());

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }
}
