package com.example.wgufinalproject;

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
import java.util.ResourceBundle;

/** ModifyPartFormController class created with initialization capabilities.
 Same functionality as AddPartForm.
 The controller interfaces with the radio buttons, labels, text fields,
 and buttons show in ModifyPartForm.fxml
 FUTURE ENHANCEMENT - The window could contain a label and text field indicating whether
 the original Part was InHouse or Outsourced as further informing the user. */
public class ModifyPartFormController implements Initializable {
    Stage stage;
    Parent scene;
    @FXML
    private TextField ModifyPartCost;

    @FXML
    private TextField ModifyPartStock;

    @FXML
    private RadioButton inRadioBtn;

    @FXML
    private Button modifyPartCxlBtn;

    @FXML
    private TextField modifyPartId;

    @FXML
    private TextField modifyPartMax;

    @FXML
    private TextField modifyPartMin;

    @FXML
    private Button modifyPartSaveBtn;

    @FXML
    private TextField modifyPartSpecial;

    @FXML
    private Label modifyPartSpecialLabel;

    @FXML
    private TextField modifypartName;

    @FXML
    private RadioButton outRadioBtn;

    @FXML
    private ToggleGroup sourceRadioGrp;

    /** This method is called after if the OutSource radio
     button is selected. The sub-label is then set to "Company Name"
     since that is a field of an Outsource object.
     @param event The event of the Outsource radio button being selected
     */
    @FXML
    void onOutSrcRadioBtnClick(ActionEvent event) {
        modifyPartSpecialLabel.setText("Company Name");
    }

    /** This method is called after if the InHouse radio
     button is selected. The sub-label is then set to "Machine ID"
     since that is a field of an InHouse object.
     @param event The event of the InHouse radio button being selected
     */
    @FXML
    void inHouseRadioBtnClick(ActionEvent event) {
        modifyPartSpecialLabel.setText("Machine ID");
    }

    /** This method is called after if the Cancel
     button is clicked. The window is switched to the MainForm.fxml file.
     @param event The event of the Cancel Button being clicked
     */
    @FXML
    void onCxlBtnClick(ActionEvent event) throws IOException {
        stage = (Stage)((Button) event.getSource()).getScene().getWindow();

        scene = FXMLLoader.load(getClass().getResource("MainForm.fxml"));

        stage.setScene(new Scene(scene));

        stage.show();
    }

    /** This method is called if the Save
     button is selected. The data is extracted from the text fields
     and used to modify the Part. Inventory is then updated.
     The window is then returned to the MainForm.fxml file.
     @param event The event of the Save button being clicked
     */
    @FXML
    void onSaveBtnClick(ActionEvent event) throws IOException {
        String updateCompanyName;
        int updateMachineId;
        Part newPart;
        int partId = 0;
        String updateName;
        int updateStock = 0;
        double updatePrice;
        int updateMin = 0;
        int updateMax = 0;

        try {
            partId = Integer.parseInt(modifyPartId.getText());
            updateName = modifypartName.getText();
            updateStock = Integer.parseInt(ModifyPartStock.getText());
            updatePrice = Double.parseDouble(ModifyPartCost.getText());
            updateMin = Integer.parseInt(modifyPartMin.getText());
            updateMax = Integer.parseInt(modifyPartMax.getText());

            if ((updateMin >= updateMax) || (updateStock <= updateMin) || (updateStock >= updateMax))
                throw new Exception();

            if (inRadioBtn.isSelected()) {
                updateMachineId = Integer.parseInt(modifyPartSpecial.getText());
                newPart = new InHouse(partId, updateName, updatePrice, updateStock, updateMin, updateMax, updateMachineId);
            } else if (outRadioBtn.isSelected()){
                updateCompanyName = modifyPartSpecial.getText();
                newPart = new Outsourced(partId, updateName, updatePrice, updateStock, updateMin, updateMax, updateCompanyName);
            } else
                throw new Exception();

            Inventory.updatePart(partId, newPart);

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
            if ((updateMin >= updateMax) || (updateStock <= updateMin) || (updateStock >= updateMax)){
                alert.setContentText("Error Max must be greater than Min and Inventory between them");
            } else {
                alert.setContentText("Please select In-House or Outsourced");
            }
            alert.showAndWait();
        }


    }

    /** This method receives a Part object to modify from the MainMenuController
     The field labels (Id, Name, etc.) are all used to set the corresponding text fields.
     @param inPart this method handles Parts of the InHouse type.
     */
    public void receiveInPart(InHouse inPart){
        inRadioBtn.setSelected(true);

        modifyPartId.setText(String.valueOf(inPart.getId()));
        modifypartName.setText(inPart.getName());
        ModifyPartStock.setText(String.valueOf(inPart.getStock()));
        ModifyPartCost.setText(String.valueOf(inPart.getPrice()));
        modifyPartMax.setText(String.valueOf(inPart.getMax()));
        modifyPartMin.setText(String.valueOf(inPart.getMin()));
        modifyPartSpecial.setText(String.valueOf(inPart.getMachineId()));

        modifyPartSpecialLabel.setText("Machine ID");

    }

    /** This method receives a Part object to modify from the MainMenuController
     The field labels (Id, Name, etc.) are all used to set the corresponding text fields.
     LOGICAL ERROR - I copied this method from the InHouse version but forgot to change
     the class to OutSourced. Eventually enough digging showed the problem.
     @param inPart this method handles Parts of the OutSource type.
     */
    public void receiveInPart(Outsourced inPart){
        outRadioBtn.setSelected(true);

        modifyPartId.setText(String.valueOf(inPart.getId()));
        modifypartName.setText(inPart.getName());
        ModifyPartStock.setText(String.valueOf(inPart.getStock()));
        ModifyPartCost.setText(String.valueOf(inPart.getPrice()));
        modifyPartMax.setText(String.valueOf(inPart.getMax()));
        modifyPartMin.setText(String.valueOf(inPart.getMin()));
        modifyPartSpecial.setText(inPart.getCompanyName());

        modifyPartSpecialLabel.setText("Company Name");

    }

    /** This method is called when the ModifyPartForm.fxml file is loaded.
     The ID text field is disabled.
     @param url The location of the relative path of the root object.
     @param resourceBundle Resource used to localize the root object; can be null if absolute path.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modifyPartId.setDisable(true);
    }
}

