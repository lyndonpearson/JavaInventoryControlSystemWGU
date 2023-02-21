package com.example.wgufinalproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
        int partId = Integer.parseInt(modifyPartId.getText());
        String updateName = modifypartName.getText();
        int updateStock = Integer.parseInt(ModifyPartStock.getText());
        double updatePrice = Double.parseDouble(ModifyPartCost.getText());
        int updateMin = Integer.parseInt(modifyPartMin.getText());
        int updateMax = Integer.parseInt(modifyPartMax.getText());

        if (inRadioBtn.isSelected()) {
            updateMachineId = Integer.parseInt(modifyPartSpecial.getText());
            newPart = new InHouse(partId, updateName, updatePrice, updateStock, updateMin, updateMax, updateMachineId);
        }else{
            updateCompanyName = modifyPartSpecial.getText();
            newPart = new Outsourced(partId, updateName, updatePrice, updateStock, updateMin, updateMax, updateCompanyName);
        }

        Inventory.updatePart(partId, newPart);
        System.out.print("Part name is: " + newPart.getName());
        stage = (Stage)((Button) event.getSource()).getScene().getWindow();

        scene = FXMLLoader.load(getClass().getResource("MainForm.fxml"));

        stage.setScene(new Scene(scene));

        stage.show();
    }

    /** This method receives a Part object to modify from the MainMenuController
     The field labels (Id, Name, etc.) are all used to set the corresponding text fields.
     @param inPart this method handles Parts of the InHouse type.
     */
    public void receiveInPart(InHouse inPart){
        modifyPartId.setText(String.valueOf(inPart.getId()));
        modifypartName.setText(inPart.getName());
        ModifyPartStock.setText(String.valueOf(inPart.getStock()));
        ModifyPartCost.setText(String.valueOf(inPart.getPrice()));
        modifyPartMax.setText(String.valueOf(inPart.getMax()));
        modifyPartMin.setText(String.valueOf(inPart.getMin()));
        modifyPartSpecial.setText(String.valueOf(inPart.getMachineId()));

    }

    /** This method receives a Part object to modify from the MainMenuController
     The field labels (Id, Name, etc.) are all used to set the corresponding text fields.
     LOGICAL ERROR - I copied this method from the InHouse version but forgot to change
     the class to OutSourced. Eventually enough digging showed the problem.
     @param inPart this method handles Parts of the OutSource type.
     */
    public void receiveInPart(Outsourced inPart){
        modifyPartId.setText(String.valueOf(inPart.getId()));
        modifypartName.setText(inPart.getName());
        ModifyPartStock.setText(String.valueOf(inPart.getStock()));
        ModifyPartCost.setText(String.valueOf(inPart.getPrice()));
        modifyPartMax.setText(String.valueOf(inPart.getMax()));
        modifyPartMin.setText(String.valueOf(inPart.getMin()));
        modifyPartSpecial.setText(inPart.getCompanyName());

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

