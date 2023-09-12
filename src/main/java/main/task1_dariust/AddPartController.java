package main.task1_dariust;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author dariustaylor
 */

public class AddPartController {

    Stage stage;
    Scene scene;

    Alert alert;


    // ADD PART FORM

    @FXML
    private AnchorPane inHouseAddPartPane;

    @FXML
    private Button inHouseCancelBtn;

    @FXML
    private Button inHouseSaveBtn;

    @FXML
    private ToggleGroup tGroup;

    @FXML
    private RadioButton outsourcedRadio;

    @FXML
    private RadioButton inHouseRadio;

    @FXML
    private Label inHouseIDLabel;

    @FXML
    private Label addPartLabel;

    @FXML
    private Label inHouseInvLabel;

    @FXML
    private Label inHouseMachineIDLabel;

    @FXML
    private Label inHouseMaxLabel;

    @FXML
    private Label inHouseMinLabel;

    @FXML
    private Label inHouseNameLabel;

    @FXML
    private Label inHousePriceLabel;

    @FXML
    private Label inHouseCompanyName;

    @FXML
    private TextField inHouseIDTF;

    @FXML
    private TextField inHouseInvTF;

    @FXML
    private TextField inHouseMachineID_TF;

    @FXML
    private TextField inHouseMaxTF;

    @FXML
    private TextField inHouseMinTF;

    @FXML
    private TextField inHouseNameTF;

    @FXML
    private TextField inHousePriceTF;

    @FXML
    private TextField inHouseCompanyNameTF;

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
     * @param event on in-house radio button pressed.
     *              shows the machine id label and text field,
     *              hides the company name label and text field.
     */
    @FXML
    void onInHouseAddPart(ActionEvent event) {
        inHouseMachineIDLabel.setVisible(true);
        inHouseMachineID_TF.setVisible(true);
        inHouseCompanyName.setVisible(false);
        inHouseCompanyNameTF.setVisible(false);


    }

    /**
     *
     * @param event on outsourced button pressed.
     *              shows the company name label and text field,
     *              hides the machine id label and text field.
     */
    @FXML
    void onOutsourcedAddPart(ActionEvent event) {
        inHouseCompanyName.setVisible(true);
        inHouseCompanyNameTF.setVisible(true);
        inHouseMachineIDLabel.setVisible(false);
        inHouseMachineID_TF.setVisible(false);

    }

    /**
     *
     * @param event on save button pressed. Adds the information into variables to be saved,
     *              based on which radio button is selected.
     *              Sends you back to main page.
     *
     *  Throws exceptions if max < min, stock < min, or stock > max
     */
    @FXML
    void SaveBtn(ActionEvent event) {
        try {
            int id = HelloApplication.GeneratePartID();
            String name = "";
            int stock = 0;
            double price = 0.0;
            int max = 0;
            int min = 0;
            int machineID = 0;
            String companyName = "";

            if (inHouseRadio.isSelected()) {
                name = inHouseNameTF.getText();
                stock = Integer.parseInt(inHouseInvTF.getText());
                price = Double.parseDouble(inHousePriceTF.getText());
                max = Integer.parseInt(inHouseMaxTF.getText());
                min = Integer.parseInt(inHouseMinTF.getText());
                machineID = Integer.parseInt(inHouseMachineID_TF.getText());

                if (max < min || stock > max || stock < min) {
                    throw new UnsupportedOperationException();
                }

                Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineID));

            } else if (outsourcedRadio.isSelected()) {
                name = inHouseNameTF.getText();
                stock = Integer.parseInt(inHouseInvTF.getText());
                price = Double.parseDouble(inHousePriceTF.getText());
                max = Integer.parseInt(inHouseMaxTF.getText());
                min = Integer.parseInt(inHouseMinTF.getText());
                companyName = inHouseCompanyNameTF.getText();

                if (max < min || stock > max || stock < min) {
                    throw new UnsupportedOperationException();
                }

                Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
            }

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Start.fxml"));
            loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (UnsupportedOperationException e) {
            alert = new Alert(Alert.AlertType.ERROR, "Max needs to be greater than min and Inv # needs to be between max and min");
            alert.setTitle("Input Error!");
            alert.showAndWait();
        } catch (Exception thrown) {
            alert = new Alert(Alert.AlertType.WARNING, "Inappropriate data entered. Enter appropriate data in text fields.");
            alert.showAndWait();
        }

    }
}
