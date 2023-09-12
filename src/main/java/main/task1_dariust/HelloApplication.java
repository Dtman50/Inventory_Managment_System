package main.task1_dariust;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


/*
    FUTURE ENHANCEMENT : To make the program a little better for error dialogs, I would add a
    confirmation dialog box at the exit as well to make sure that the user is sure that they
    want to exit the program. I would also incorporate the use of databases to save information
    better.
 */

public class HelloApplication extends Application {
    public static int partIndex = 0;
    public static int prodIndex = 0;

    /**
     *
     * @return part id
     */
    public static int GeneratePartID() {
        return ++partIndex;
    }

    /**
     *
     * @return product id
     */
    public static int GenerateProdID() {
        return ++prodIndex;
    }

    /**
     *
     * @param stage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws IOException error handling for if the scene won't load
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Start.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 980, 375);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /**
     *
     * @param args main application method
     */
    /*
        JAVADOCS : Located inside the Task1_DariusT folder directory.
    */
    public static void main(String[] args) {

        ObservableList<Part> associated = FXCollections.observableArrayList();
        Part part1 = new InHouse(GeneratePartID(), "Controller", 399.99, 15, 1, 30, 111);

        Part part2 = new InHouse(GeneratePartID(), "Remote", 299.99, 10, 1, 20, 222);

        Part part3 = new InHouse(GeneratePartID(), "Keyboard", 499.99, 5, 1, 10, 333);
        Part part4 = new Outsourced(GeneratePartID(), "Headset", 199.99, 40, 1, 100, "Logitech");
        Part part5 = new Outsourced(GeneratePartID(), "Mouse", 59.99, 35, 1, 40, "Orbi");

        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        Inventory.addPart(part5);

        Product product1 = new Product(associated, GenerateProdID(), "PS5", 499.99, 15, 1, 30);
        Product product2 = new Product(associated, GenerateProdID(), "Xbox", 399.99, 10, 1, 20);
        Product product3 = new Product(associated, GenerateProdID(), "Switch", 499.99, 5, 1, 10);
        Product product4 = new Product(associated, GenerateProdID(), "Atari", 59.99, 40, 1, 100);
        Product product5 = new Product(associated, GenerateProdID(), "PS-Vita", 199.99, 35, 1, 40);

        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);
        Inventory.addProduct(product4);
        Inventory.addProduct(product5);



        launch(args);
    }
}