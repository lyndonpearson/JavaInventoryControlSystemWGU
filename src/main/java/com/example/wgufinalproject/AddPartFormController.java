package com.example.wgufinalproject;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

/** AddPartFormController class created with initialization capabilities.
    The controller interfaces with the radio buttons, labels, text fields,
    and buttons show in AddPartForm.fxml
    */
public class AddPartFormController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private Button cancelButton;

    @FXML
    private Label idLabel;

    @FXML
    private TextField idTextField;

    @FXML
    private RadioButton inHouseRadioBtn;

    @FXML
    private Label invLabel;

    @FXML
    private TextField invTextField;

    @FXML
    private Label maxLabel;

    @FXML
    private TextField maxTextField;

    @FXML
    private Label minLabel;

    @FXML
    private TextField minTextField;

    @FXML
    private Label nameLabel;

    @FXML
    private TextField nameTextField;

    @FXML
    private RadioButton outsrcRadioBtn;

    @FXML
    private Label priceLabel;

    @FXML
    private TextField priceTextField;

    @FXML
    private Button saveButton;

    @FXML
    private Label subIdLabel;

    @FXML
    private TextField subIdTextField;

    @FXML
    private ToggleGroup partToggleGroup;

    /** This method is called after if the OutSource radio
     button is selected. The sub-label is then set to "Company Name"
     since that is a field of an Outsource object.
     @param event The event of the Outsource radio button being selected
     */
    @FXML
    void onOutSrcRadioBtnClick(ActionEvent event) {
            subIdLabel.setText("Company Name");
    }

    /** This method is called after if the InHouse radio
     button is selected. The sub-label is then set to "Machine ID"
     since that is a field of an InHouse object.
     @param event The event of the InHouse radio button being selected
     */
    @FXML
    void inHouseRadioBtnClick(ActionEvent event) {
        subIdLabel.setText("Machine ID");
    }

    /** This method is called after if the Cancel
     button is clicked. The window is switched to the MainForm.fxml file.
     @param event The event of the Cancel Button being clicked
     */
    @FXML
    void onCancelButtonClick(ActionEvent event) throws IOException {
        stage = (Stage)((Button) event.getSource()).getScene().getWindow();

        scene = FXMLLoader.load(getClass().getResource("MainForm.fxml"));

        stage.setScene(new Scene(scene));

        stage.show();
    }
    /** This method is called if the Save
     button is selected. The text fields are validated and, if all correct,
     the data is extracted to create a new Part and add it to inventory. The
     Part is either Outsource or InHouse type, determined by the radio button
     selection. The window is then returned to the MainForm.fxml file.
     RUNTIME ERROR - The warning message related to RadioButton selection
     wasn't working properly. I referenced some course material to create
     my own exception to be throw/caught to handle this situation.
     @param event The event of the Save button being clicked
     */
    @FXML
    void onSaveButtonClick(ActionEvent event) throws IOException {

        try{
            int id = Integer.parseInt(idTextField.getText());
            String name = nameTextField.getText();
            int inv = Integer.parseInt(invTextField.getText());
            double price = Double.parseDouble(priceTextField.getText());
            int max = Integer.parseInt(maxTextField.getText());
            int min = Integer.parseInt(minTextField.getText());
            int machineId;
            String companyName;
            if (partToggleGroup.getSelectedToggle() == inHouseRadioBtn) {
                machineId = Integer.parseInt(subIdTextField.getText());
                Inventory.addPart(new InHouse(id, name, price, inv, min, max, machineId));
            } else if (partToggleGroup.getSelectedToggle() == outsrcRadioBtn) {
                subIdLabel.setText("Company Name");
                companyName = subIdTextField.getText();
                Inventory.addPart(new Outsourced(id, name, price, inv, min, max, companyName));
            } else
                throw new Exception();

            stage = (Stage)((Button) event.getSource()).getScene().getWindow();

            scene = FXMLLoader.load(getClass().getResource("MainForm.fxml"));

            stage.setScene(new Scene(scene));

            stage.show();

        } catch(NumberFormatException inputError){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please enter a valid value for each text field");
            alert.showAndWait();
        } catch (Exception msg) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please select In-House or Outsourced");
            alert.showAndWait();
        }
    }

    /** This method is called when the AddPartForm.fxml file is loaded.
     The ID text field is disabled and a unique one is assigned.
     @param url The location of the relative path of the root object.
     @param resourceBundle Resource used to localize the root object; can be null if absolute path.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idTextField.setDisable(true);
        System.out.println(url);
        System.out.println(resourceBundle);

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
        idTextField.setText(Integer.toString(nextId));
    }
}
