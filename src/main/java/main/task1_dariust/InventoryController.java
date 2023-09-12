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

public class InventoryController implements Initializable {

    private static int id = 0;
    private static String name = "";
    private static int stock = 0;
    private static double price = 0.0;
    private static int min = 0;
    private static int max = 0;
    private Scene scene;
    private Stage stage;
    Alert alert;

    public static ObservableList<Part> associated = FXCollections.observableArrayList();
    public static Product product = new Product(associated, id, name, price, stock, min, max);

    // MAIN FORM

    @FXML
    private AnchorPane mainProdPane;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private AnchorPane mainPartsPane;

    @FXML
    private Button mainExitBtn;

    @FXML
    private Button mainPartsAddBtn;

    @FXML
    private Button mainPartsDelBtn;

    @FXML
    private Button mainPartsModBtn;

    @FXML
    private Button mainProdAddBtn;

    @FXML
    private Button mainProdDelBtn;

    @FXML
    private Button mainProdModBtn;

    @FXML
    private Label mainIMSLabel;

    @FXML
    private Label mainPartsLabel;

    @FXML
    private Label mainProdLabel;

    @FXML
    private TextField mainPartsSearch;

    @FXML
    private TextField mainProdSearch;

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
    private TableColumn<Product, Integer> prodIDcol;

    @FXML
    private TableColumn<Product, Integer> prodInvLevelCol;

    @FXML
    private TableColumn<Product, String> prodNameCol;

    @FXML
    private TableColumn<Product, Double> prodPriceCol;

    @FXML
    private TableView<Product> prodTableView;


    /**
     *
     * @param event on add button press on the part table view.
     *              Sends you to the Add part screen.
     * @throws IOException exception handling if loading the part screen fails
     */
    @FXML
    void AddParts(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AddPart.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load(), 675, 650);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /**
     *
     * @param event on key pressed on the part table view search bar. searches view by id and name
     *              and displays result.
     * <p>
     * RUNTIME ERROR : When entering letters into the search box that do not match any of the
     *              results, you get a NUMBER FORMAT EXCEPTION error and that was corrected by
     *              surrounding the code block in a try/catch statement and catching the error
     *              but just ignoring it. So then you don't receive the error in the debugger.
     * </p>
     */
    @FXML
    void onSearchPart(ActionEvent event) {
        try {
            ObservableList<Part> searched = Inventory.lookupPart(mainPartsSearch.getText());

            if (searched.size() == 0) {
                int id = Integer.parseInt(mainPartsSearch.getText());
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
     * @param event on key pressed on the product table view search bar. searches view by id and name
     *              and displays result.
     */
    @FXML
    void onSearchProd(ActionEvent event) {
        try {
            ObservableList<Product> searched = Inventory.lookupProduct(mainProdSearch.getText());

            if (searched.size() == 0) {
                int id = Integer.parseInt(mainProdSearch.getText());
                Product prod = Inventory.lookupProduct(id);
                if (prod != null) {
                    searched.add(prod);
                }
                if (searched.size() == 0) {
                    alert = new Alert(Alert.AlertType.WARNING, "No Results. Try again.");
                    alert.showAndWait();
                }

            }

            prodTableView.setItems(searched);

        } catch (Exception e) {
            alert = new Alert(Alert.AlertType.WARNING, "No Results. Try again.");
            alert.showAndWait();
        }
    }

    /**
     *
     * @param event on add button press on the product table view.
     *              Sends you to the Add product screen.
     * @throws IOException exception handling if loading the product screen fails
     */
    @FXML
    void AddProduct(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AddProduct.fxml"));
        loader.load();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     *
     * @param event on delete button pressed on the part table view. Deletes selected part.
     *              refreshes list and displays the other parts
     *
     */
    @FXML
    void DeleteParts(ActionEvent event) {
        Part selectedItem;
        selectedItem = partsTableView.getSelectionModel().getSelectedItem();
        Alert alert;

        if (selectedItem == null) {
            alert = new Alert(Alert.AlertType.ERROR, "Select a part to be deleted.");
            alert.showAndWait();
        }
        else {
            alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(selectedItem);
                partsTableView.setItems(Inventory.getAllParts());
            }
        }

    }

    /**
     *
     * @param event on delete button pressed on the product table view. Deletes selected product.
     *              refreshes list and displays the other products
     *
     */
    @FXML
    void DeleteProduct(ActionEvent event) {
        Product selectedProd = prodTableView.getSelectionModel().getSelectedItem();
        Alert alert;

        if (selectedProd == null) {
            alert = new Alert(Alert.AlertType.ERROR, "Select a product to be deleted.");
            alert.showAndWait();
        } else if (selectedProd.getAllAssociatedParts().isEmpty()) {
            alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deleteProduct(selectedProd);
                prodTableView.setItems(Inventory.getAllProducts());
            }

        }
        else {
            alert = new Alert(Alert.AlertType.ERROR, "The Product can't be deleted because it has associated parts");
            alert.showAndWait();
        }

    }

    /**
     *
     * @param event on exit button press.
     *              exits the program.
     */
    @FXML
    void ExitProgram(ActionEvent event) {

        Node source = (Node) event.getSource();
        stage = (Stage) source.getScene().getWindow();
        stage.close();

    }

    /**
     *
     * @param event on modify button press on part table view. Sends you to modify part screen.
     *              Passes information to the modify part screen based on which radio button is
     *              selected.
     * @throws IOException exception handling if the modify part page does not load properly.
     */
    @FXML
    void ModifyParts(ActionEvent event) throws IOException {
        Part selected = partsTableView.getSelectionModel().getSelectedItem();
        int selectedIndex = partsTableView.getSelectionModel().getSelectedIndex();

        if (selected == null) {
            alert = new Alert(Alert.AlertType.ERROR, "Select a part to be modified.");
            alert.showAndWait();
        }
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ModifyPart.fxml"));
            loader.load();

            ModifyPartController MPController = loader.getController();
            if (selected.getClass().getName().equals("main.task1_dariust.InHouse")) {
                MPController.toMod((InHouse) selected);
            } else if (selected.getClass().getName().equals("main.task1_dariust.Outsourced")) {
                MPController.toMod((Outsourced) selected);
            }

            ModifyPartController MPControllerIndex = loader.getController();
            MPControllerIndex.modIndex(selectedIndex);

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NullPointerException e) {
            //Already Handled
        }


    }

    /**
     *
     * @param event on modify button press on product table view. Sends you to modify product screen.
     *              Passes information to the modify product screen.
     * @throws IOException exception handling if the modify product page does not load properly.
     */
    @FXML
    void ModifyProduct(ActionEvent event) throws IOException {
        Product selected = prodTableView.getSelectionModel().getSelectedItem();
        int selectedIndex = prodTableView.getSelectionModel().getSelectedIndex();

        if (selected == null) {
            alert = new Alert(Alert.AlertType.ERROR, "Select a product to be modified.");
            alert.showAndWait();
        }

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ModifyProduct.fxml"));
            loader.load();

            ModifyProductController MProdController = loader.getController();
            MProdController.toModProd(selected);

            ModifyProductController MProdControllerIndex = loader.getController();
            MProdControllerIndex.toModIndex(selectedIndex);

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NullPointerException e) {
            //Already Handled
        }

    }

    /**
     * Called to initialize a controller after its root element has been completely processed.
     *  Shows the full parts and product lists.
     *
     * @param url location - The location used to resolve relative paths for the root object,
     *            or null if the location is not known. *Java8 docs*
     * @param resourceBundle resources - The resources used to localize the root object,
     *                       or null if the root object was not localized. *Java8 docs*
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // PARTS VIEW
        partsIDcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        partsTableView.setItems(Inventory.getAllParts());

        //PRODUCTS VIEW
        prodIDcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        prodTableView.setItems(Inventory.getAllProducts());
    }

}
