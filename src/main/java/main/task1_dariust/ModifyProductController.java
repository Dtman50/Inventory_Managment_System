package main.task1_dariust;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 *
 * @author dariustaylor
 */

public class ModifyProductController implements Initializable {

    int index = 0;
    public int id = 0;
    public String name = "";
    public int stock = 0;
    public double price = 0.0;
    public int min = 0;
    public int max = 0;

    Stage stage;
    Scene scene;
    Alert alert;

    ObservableList<Part> associated = FXCollections.observableArrayList();

    Product product;


    // MODIFY PRODUCT FORM

    @FXML
    private AnchorPane modProdPane;

    @FXML
    private Button modAddProductBtn;

    @FXML
    private Button modProdCancelBtn;

    @FXML
    private Button modRemoveAssociatedBtn;

    @FXML
    private Button modProdSaveBtn;

    @FXML
    private Label modProdIDLabel;

    @FXML
    private Label modProdInvLabel;

    @FXML
    private Label modProdLabel;

    @FXML
    private Label modProdMinLabel;

    @FXML
    private Label modProdMaxLabel;

    @FXML
    private Label modProdPriceLabel;

    @FXML
    private Label modProdNameLabel;

    @FXML
    private TextField modProdIDTF;

    @FXML
    private TextField modProdInvTF;

    @FXML
    private TextField modProdMaxTF;

    @FXML
    private TextField modProdMinTF;

    @FXML
    private TextField modProdNameTF;

    @FXML
    private TextField modProdPriceTF;

    @FXML
    private TextField modProdSearch;

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
    private TableColumn<Part, Integer> associatedPartsIDcol;

    @FXML
    private TableColumn<Part, Integer> associatedPartsInvCol;

    @FXML
    private TableColumn<Part, String> associatedPartsNameCol;

    @FXML
    private TableColumn<Part, Double> associatedPartsPriceCol;

    @FXML
    private TableView<Part> modAssociatedTable;


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
            ObservableList<Part> searched = Inventory.lookupPart(modProdSearch.getText());

            if (searched.size() == 0) {
                int id = Integer.parseInt(modProdSearch.getText());
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
     * @param event on save button press. takes info for text fields, creates a new object, and
     *              changes the previous object to that new object. Also adds bottom list items
     *              to the products new object.
     *              Sends you back to the main screen.
     * @throws IOException exception handler if the main screen does not load properly.
     */
    @FXML
    void SaveBtn(ActionEvent event) throws IOException {
        try {
            ObservableList<Part> addedToProduct = FXCollections.observableArrayList();

            int i = toModIndex(index);

            id = Integer.parseInt(modProdIDTF.getPromptText());
            name = modProdNameTF.getText();
            stock = Integer.parseInt(modProdInvTF.getText());
            price = Double.parseDouble(modProdPriceTF.getText());
            max = Integer.parseInt(modProdMaxTF.getText());
            min = Integer.parseInt(modProdMinTF.getText());

            if (max < min || stock > max || stock < min) {
                throw new UnsupportedOperationException();
            }

            Product prod = new Product(addedToProduct, id, name, price, stock, min, max);

            for(Part part : associated) {
                prod.addAssociatedPart(part);
            }

            Inventory.updateProduct(i, prod);
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
     *  Shows the full parts list on load and the associated parts list from added parts list.
     *  If the product is being modified, once a new part is added from the top list, the bottom
     *  list clears.
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
//        modAssociatedTable.setItems(associated);

    }

    /**
     *
     * @param prod passed reference from the main screen. Loads the selected products information
     *             into the text fields of the modify product screen along with any associated parts
     */
    public void toModProd(Product prod) {
        modProdIDTF.setPromptText(String.valueOf(prod.getId()));
        modProdNameTF.setText(prod.getName());
        modProdInvTF.setText(String.valueOf(prod.getStock()));
        modProdPriceTF.setText(String.valueOf(prod.getPrice()));
        modProdMaxTF.setText(String.valueOf(prod.getMax()));
        modProdMinTF.setText(String.valueOf(prod.getMin()));

        associated = prod.getAllAssociatedParts();
        modAssociatedTable.setItems(associated);

        product = prod;
    }

    /**
     *
     * @param index passed index reference from the main screen.
     *              used to modify the products.
     * @return the index of the selected product.
     */
    public int toModIndex(int index) {
        this.index = index;
        return index;
    }
}
