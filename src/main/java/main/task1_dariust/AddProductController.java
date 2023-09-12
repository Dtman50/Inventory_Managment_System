package main.task1_dariust;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 *
 * @author dariustaylor
 */

public class AddProductController implements Initializable {

    Stage stage;
    Scene scene;
    Alert alert;

    ObservableList<Part> associated = FXCollections.observableArrayList();
    ObservableList<Part> addedToProduct = FXCollections.observableArrayList();


    // ADD PRODUCT FORM

    @FXML
    private AnchorPane addProdPane;

    @FXML
    private Button addProductBtn;

    @FXML
    private Button addProdCancelBtn;

    @FXML
    private Button addProdSaveBtn;

    @FXML
    private Label addProdInvLabel;

    @FXML
    private Label addProdIDLabel;

    @FXML
    private Label addProdLabel;

    @FXML
    private Label addProdMaxLabel;

    @FXML
    private Label addProdMinLabel;

    @FXML
    private Label addProdPriceLabel;

    @FXML
    private Label addProdNameLabel;

    @FXML
    private TextField addProdIDTF;

    @FXML
    private TextField addProdInvTF;

    @FXML
    private TextField addProdMaxTF;

    @FXML
    private TextField addProdMinTF;

    @FXML
    private TextField addProdNameTF;

    @FXML
    private TextField addProdPriceTF;

    @FXML
    private TextField addProdSearch;

    @FXML
    private TableColumn<Part, Integer> associatedPartsIDcol;

    @FXML
    private TableColumn<Part, Integer> associatedPartsInvCol;

    @FXML
    private TableColumn<Part, String> associatedPartsNameCol;

    @FXML
    private TableColumn<Part, Double> associatedPartsPriceCol;

    @FXML
    private TableView<Part> modAssociatedTable;

    @FXML
    private TableColumn<Part, Integer> partsIDcol;

    @FXML
    private TableColumn<Part, Integer> partsInvLevelCol;

    @FXML
    private TableColumn<Part, String> partsNameCol;

    @FXML
    private TableColumn<Part, Double> partsPriceCol;

    @FXML
    private TableView<Part> partsTableView;

    @FXML
    private Button removeAssociatedBtn;

    /**
     *
     * @param event on add button pressed. Moves selected part to the bottom table view.
     */
    @FXML
    void toAssociated(ActionEvent event) {
        Part selected = partsTableView.getSelectionModel().getSelectedItem();
        if (!(selected == null)) {
            associated.add(selected);
        }

    }

    /**
     *
     * @param event on key pressed. Searches the list of parts by name or id.
     *              displays result.
     */
    @FXML
    void onSearchPart(ActionEvent event) {
        try {
            ObservableList<Part> searched = Inventory.lookupPart(addProdSearch.getText());

            if (searched.size() == 0) {
                int id = Integer.parseInt(addProdSearch.getText());
                Part part = Inventory.lookupPart(id);
                if (part != null) {
                    searched.add(part);
                }
                if (searched.size() == 0) {
                    alert = new Alert(Alert.AlertType.WARNING, "No Results. Try again.");
                    alert.showAndWait();
                }
            }

            partsTableView.setItems(searched);
        } catch (Exception e) {
            alert = new Alert(Alert.AlertType.WARNING, "No Results. Try again.");
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
     * @param event on Remove button press. Deletes the selected part for bottom list.
     */
    @FXML
    void RemoveAssociatedPart(ActionEvent event) {
        Part selectedAssociated = modAssociatedTable.getSelectionModel().getSelectedItem();
        alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            associated.remove(selectedAssociated);
        }

    }

    /**
     *
     * @param event on save button pressed.
     *              Adds the information from text fields into variables to be saved.
     *              Sends you back to main page.
     *
     */
    @FXML
    void SaveBtn(ActionEvent event) {

        try {
            int id = HelloApplication.GenerateProdID();
            String name = addProdNameTF.getText();
            int stock = Integer.parseInt(addProdInvTF.getText());
            double price = Double.parseDouble(addProdPriceTF.getText());
            int max = Integer.parseInt(addProdMaxTF.getText());
            int min = Integer.parseInt(addProdMinTF.getText());

            if (max < min || stock > max || stock < min) {
                throw new UnsupportedOperationException();
            }

            Product prod = new Product(addedToProduct, id, name, price, stock, min, max);

            for(Part part : associated) {
                prod.addAssociatedPart(part);
            }

            Inventory.addProduct(prod);
            associated.clear();

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Start.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(fxmlLoader.load(), 980, 375);
            stage.setTitle("Inventory Management System");
            stage.setScene(scene);
            stage.show();

        } catch (UnsupportedOperationException e) {
            alert = new Alert(Alert.AlertType.ERROR, "Max needs to be greater than min and Inv # needs to be between max and min");
            alert.setTitle("Input Error!");
            alert.showAndWait();
        } catch (Exception e) {
            alert = new Alert(Alert.AlertType.WARNING, "Inappropriate data entered. Enter appropriate data in text fields.");
            alert.showAndWait();
        }
    }

    /**
     * Called to initialize a controller after its root element has been completely processed.
     *  Shows the full parts list and the associated parts list from added parts list.
     *
     * @param url location - The location used to resolve relative paths for the root object,
     *            or null if the location is not known. *Java8 docs*
     * @param resourceBundle resources - The resources used to localize the root object,
     *                       or null if the root object was not localized. *Java8 docs*
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsTableView.setItems(Inventory.getAllParts());
        partsIDcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartsIDcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        modAssociatedTable.setItems(associated);

    }



}