package main.task1_dariust;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 *
 * @author dariustaylor
 */

public class ModifyPartController {

    public int index = 0;
    Alert alert;


    // MODIFY PART FORM

    @FXML
    private AnchorPane inHouseModPartPane;

    @FXML
    private Button modPartSaveBtn;

    @FXML
    private Button modPartCancelBtn;

    @FXML
    private ToggleGroup tGroup;

    @FXML
    private RadioButton modPartOutsourcedRadio;

    @FXML
    private RadioButton modPartInHouseRadio;

    @FXML
    private Label modPartInvLabel;

    @FXML
    private Label modPartIDLabel;

    @FXML
    private Label modPartLabel;

    @FXML
    private Label modPartMachineIDLabel;

    @FXML
    private Label modPartMaxLabel;

    @FXML
    private Label modPartMinLabel;

    @FXML
    private Label modPartNameLabel;

    @FXML
    private Label modPartPriceLabel;

    @FXML
    private Label modCompanyNameLabel;

    @FXML
    private TextField modPartIDTF;

    @FXML
    private TextField modPartInvTF;

    @FXML
    private TextField modPartMachineID_TF;

    @FXML
    private TextField modPartMaxTF;

    @FXML
    private TextField modPartMinTF;

    @FXML
    private TextField modPartNameTF;

    @FXML
    private TextField modPartPriceTF;

    @FXML
    private TextField modCompanyNameTF;

    /**
     *
     * @param event on in-house radio button pressed.
     *              shows the machine id label and text field,
     *              hides the company name label and text field.
     */
    @FXML
    void modOnInHouse(ActionEvent event) {
        modPartMachineIDLabel.setVisible(true);
        modPartMachineID_TF.setVisible(true);
        modCompanyNameLabel.setVisible(false);
        modCompanyNameTF.setVisible(false);

    }

    /**
     *
     * @param event on outsourced button pressed.
     *              shows the company name label and text field,
     *              hides the machine id label and text field.
     */
    @FXML
    void modOnOutsourced(ActionEvent event) {
        modPartMachineIDLabel.setVisible(false);
        modPartMachineID_TF.setVisible(false);
        modCompanyNameLabel.setVisible(true);
        modCompanyNameTF.setVisible(true);
    }

    /**
     *
     * @param event on save button press. takes info for text fields, creates a new object, and
     *              changes the previous object to that new object. Based on the radio button selected.
     *              Sends you back to the main screen.
     * @throws IOException exception handler if the main screen does not load properly.
     */
    @FXML
    void onModSave(ActionEvent event) throws IOException {

        try {
            int id = Integer.parseInt(modPartIDTF.getPromptText());
            int i  = modIndex(index);
            if (modPartInHouseRadio.isSelected()) {
                String name = modPartNameTF.getText();
                int stock = Integer.parseInt(modPartInvTF.getText());
                double price = Double.parseDouble(modPartPriceTF.getText());
                int min = Integer.parseInt(modPartMinTF.getText());
                int max = Integer.parseInt(modPartMaxTF.getText());
                int machineID = Integer.parseInt(modPartMachineID_TF.getText());

                if (max < min || stock > max || stock < min) {
                    throw new UnsupportedOperationException();
                }

                Inventory.updatePart(i, new InHouse(id, name, price, stock, min, max, machineID));
            } else if (modPartOutsourcedRadio.isSelected()) {
                String name = modPartNameTF.getText();
                int stock = Integer.parseInt(modPartInvTF.getText());
                double price = Double.parseDouble(modPartPriceTF.getText());
                int min = Integer.parseInt(modPartMinTF.getText());
                int max = Integer.parseInt(modPartMaxTF.getText());
                String companyName = modCompanyNameTF.getText();

                if (max < min || stock > max || stock < min) {
                    throw new UnsupportedOperationException();
                }

                Inventory.updatePart(i, new Outsourced(id, name, price, stock, min, max, companyName));
            }

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Start.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load(), 980, 375);
            stage.setTitle("Inventory Management System");
            stage.setScene(scene);
            stage.show();
        } catch (UnsupportedOperationException e) {
            alert = new Alert(Alert.AlertType.ERROR, "Max needs to be less than min and Inv # needs to be between max and min");
            alert.setTitle("Input Error!");
            alert.showAndWait();
        } catch (Exception e) {
            alert = new Alert(Alert.AlertType.WARNING, "Inappropriate data entered. Enter appropriate data in text fields.");
            alert.showAndWait();
        }
    }

    /**
     *
     * @param event on cancel button pressed. returns to main screen.
     * @throws IOException exception handling if the main page doesn't load
     */
    @FXML
    void CancelBtn(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Start.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 980, 375);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /**
     *
     * @param part passed selected part from the main screen.
     *             based on if the class is in-house or outsourced
     *             , the radio button, text fields, and labels
     *             are adjusted respectively.
     */
    public void toMod(Part part) {
        if (part.getClass().getName().equals("main.task1_dariust.InHouse")) {
            InHouse inHouse = (InHouse) part;

            modPartInHouseRadio.setSelected(true);
            modPartMachineIDLabel.setVisible(true);
            modPartMachineID_TF.setVisible(true);
            modCompanyNameLabel.setVisible(false);
            modCompanyNameTF.setVisible(false);
            modPartIDTF.setPromptText(String.valueOf(part.getId()));
            modPartNameTF.setText(part.getName());
            modPartInvTF.setText(String.valueOf(part.getStock()));
            modPartPriceTF.setText(String.valueOf(part.getPrice()));
            modPartMaxTF.setText(String.valueOf(part.getMax()));
            modPartMinTF.setText(String.valueOf(part.getMin()));
            modPartMachineID_TF.setText(String.valueOf((inHouse.getMachineID())));

        } else if (part.getClass().getName().equals("main.task1_dariust.Outsourced")) {
            Outsourced outsourced = (Outsourced) part;

            modPartOutsourcedRadio.setSelected(true);
            modPartMachineIDLabel.setVisible(false);
            modPartMachineID_TF.setVisible(false);
            modCompanyNameLabel.setVisible(true);
            modCompanyNameTF.setVisible(true);
            modPartIDTF.setPromptText(String.valueOf(part.getId()));
            modPartNameTF.setText(part.getName());
            modPartInvTF.setText(String.valueOf(part.getStock()));
            modPartPriceTF.setText(String.valueOf(part.getPrice()));
            modPartMaxTF.setText(String.valueOf(part.getMax()));
            modPartMinTF.setText(String.valueOf(part.getMin()));
            modCompanyNameTF.setText(outsourced.getCompanyName());
        }
    }

    /**
     *
     * @param index passed index from the main screen
     * @return the index of the selected part.
     */
    public int modIndex(int index) {
        this.index = index;
        return index;
    }

}
