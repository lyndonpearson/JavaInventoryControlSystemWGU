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
import java.util.ResourceBundle;
/** MainMenuController class created with initialization capabilities.
 The controller interfaces with the text fields, TableViews,
 and buttons for both Parts and Products as the front-facing GUI
 of the inventory system
 FUTURE ENHANCEMENT - Adding a visualization of Product/Part association would be useful.
 A third window in the bottom could be populated with Products and their associated parts.
 The window would likely need scroll options as it could contain a large amount of data/rows.
 */
public class MainMenuController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private Button addPartButton;

    @FXML
    private Button addProductButton;

    @FXML
    private Button deletePartButton;

    @FXML
    private Button deleteProductButton;

    @FXML
    private Button modifyPartButton;

    @FXML
    private Button modifyProductButton;

    @FXML
    private TableColumn<?, ?> partIdCol;

    @FXML
    private TableColumn<?, ?> partInventoryCol;

    @FXML
    private TableColumn<?, ?> partNameCol;

    @FXML
    private TableColumn<?, ?> partPriceCol;

    @FXML
    private TableView<Part> partsTableView;

    @FXML
    private TableView<Product> productTableView;

    @FXML
    private TableColumn<?, ?> productIdCol;

    @FXML
    private TableColumn<?, ?> productNameCol;

    @FXML
    private TableColumn<?, ?> productPriceCol;

    @FXML
    private TableColumn<?, ?> productInventoryCol;

    @FXML
    private TextField searchPartField;

    @FXML
    private TextField searchProductField;

    /** This method is called after if the Add Part
     Button is clicked. The window is switched to the AddPartForm.fxml file.
     @param event The event of the Add Part Button being clicked
     */
    @FXML
    void onAddPartButtonClick(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        scene = FXMLLoader.load(getClass().getResource("AddPartForm.fxml"));

        stage.setScene(new Scene(scene));

        stage.show();

    }
    /** This method is called after if the Modify Part
     Button is clicked.
     The type of Part is checked (InHouse or OutSourced) and the appropriate
     fields are sent to the ModifyPartFormController.
     The window is switched to the ModifyPartForm.fxml file.
     @param event The event of the Modify Part Button being clicked
     */
    @FXML
    void onModifyPartButtonClick(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ModifyPartForm.fxml"));
        loader.load();
        ModifyPartFormController MPFController = loader.getController();

        if (partsTableView.getSelectionModel().getSelectedItem() instanceof InHouse)
            MPFController.receiveInPart((InHouse) partsTableView.getSelectionModel().getSelectedItem());
        else if (partsTableView.getSelectionModel().getSelectedItem() instanceof Outsourced)
            MPFController.receiveInPart((Outsourced) partsTableView.getSelectionModel().getSelectedItem());

        stage = (Stage)((Button) event.getSource()).getScene().getWindow();

        Parent scene = loader.getRoot();

        stage.setScene(new Scene(scene));

        stage.show();

    }
    /** This method is called after the Delete Part
     Button is clicked. After the user confirms, the selected
     Part is removed from inventory.
     @param event The event of the Delete Part Button being clicked
     */
    @FXML
    void onDeletePartButtonClick(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete part," +
                "do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            Part deletedPart = partsTableView.getSelectionModel().getSelectedItem();
            Inventory.deletePart(deletedPart);
        }

    }
    /** This method is called when the Search Field is selected.
     Any existing data present in the field is removed.
     @param event The event of the Search Field being selected.
     */
    @FXML
    void clearSearchPartField(MouseEvent event) {
        searchPartField.clear();
    }

    /** This method is called if anything is typed in the Search Field.
     If no ID or Part Name matches the entry, an error is displayed
     otherwise matches are displayed in Parts TableView.
     If the Search field is cleared, the Parts TableView is re-populated
     with the original data.
     @param event The event when data is entered in the Search Field.
     */
    @FXML
    void searchPartFieldSelected(KeyEvent event) {

        ObservableList<Part> filteredList = FXCollections.observableArrayList();
        String partValue = searchPartField.getText();

        filteredList = Inventory.lookupPart(partValue);

        if (filteredList.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product Error Dialog");
            alert.setContentText("Error No Results Found");
            alert.showAndWait();
        }else {
            partsTableView.setItems(filteredList);
            partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        }

        if (partValue.isEmpty())
            partsTableView.setItems(Inventory.getAllParts());
    }

    /** This method is called when the Search Field is selected.
     Any existing data present in the field is removed.
     @param event The event of the Search Field being selected.
     */
    @FXML
    void clearSearchProductField(MouseEvent event) {
        searchProductField.clear();
    }

    /** This method is called after if the Add Product
     Button is clicked. The window is switched to the AddProductForm.fxml file.
     @param event The event of the Add Product Button being clicked
     */
    @FXML
    void onAddProductButtonClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        scene = FXMLLoader.load(getClass().getResource("AddProductForm.fxml"));

        stage.setScene(new Scene(scene));

        stage.show();
    }

    /** This method is called after the Delete Product
     Button is clicked. After the user confirms, the selected
     Product is removed from inventory.
     @param event The event of the Delete Product Button being clicked
     */
    @FXML
    void onDeleteProductButtonClick(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete product," +
                "do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            Product deletedProduct = productTableView.getSelectionModel().getSelectedItem();
            Inventory.deleteProduct(deletedProduct);
        }
    }

    /** This method is called after if the Modify Product
     Button is clicked.
     The selected part's data is sent to the ModifyProductFormController.
     The window is switched to the ModifyProductForm.fxml file.
     @param event The event of the Modify Product Button being clicked
     */
    @FXML
    void onModifyProductButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ModifyProductForm.fxml"));
        loader.load();
        ModifyProductFormController MPFController = loader.getController();

        MPFController.receiveProduct(productTableView.getSelectionModel().getSelectedItem());

        stage = (Stage)((Button) event.getSource()).getScene().getWindow();

        Parent scene = loader.getRoot();

        stage.setScene(new Scene(scene));

        stage.show();

    }

    /** This method is called if anything is typed in the Search Field.
     If no ID or Product Name matches the entry, an error is displayed
     otherwise matches are displayed in Product TableView.
     If the Search field is cleared, the Product TableView is re-populated
     with the original data.
     @param event The event when data is entered in the Search Field.
     */
    @FXML
    void searchProductFieldSelected(KeyEvent event) {
        ObservableList<Product> filteredListProduct = FXCollections.observableArrayList();
        String testValue = searchProductField.getText();

        filteredListProduct = Inventory.lookupProduct(testValue);

        if (filteredListProduct.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product Error Dialog");
            alert.setContentText("Error No Results Found");
            alert.showAndWait();
        }else {
            productTableView.setItems(filteredListProduct);
            productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        }

        if (testValue.isEmpty())
            productTableView.setItems(Inventory.getAllProducts());
    }

    /** This method is called when the Exit Button is clicked.
     The program is then terminated.
     @param event The event when the Exit Button is clicked.
     */
    @FXML
    void onExitMainButtonClick(ActionEvent event) {
        System.exit(0);
    }

    /** This method is called when the MainForm.fxml file is loaded.
     The TableViews (Parts and Products) are populated with inventory data.
     LOGICAL ERROR - Was receiving inconsistent data throughout the pages. Eventually
     discovered in the setItems call for ProductTableView, I was using the method getAllParts
     instead of getAllProducts.
     @param url The location of the relative path of the root object.
     @param resourceBundle Resource used to localize the root object; can be null if absolute path.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsTableView.setItems(Inventory.getAllParts());
        productTableView.setItems(Inventory.getAllProducts());

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

    }
}