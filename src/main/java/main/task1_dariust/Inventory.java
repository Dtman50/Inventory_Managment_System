package main.task1_dariust;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Darius Taylor
 */

public class Inventory {
    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     *
     * @param newPart add part to list
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     *
     * @param newProduct add product to list
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     *
     * @param partID look up the part id
     * @return the part id or null if there is no match
     */
    public static Part lookupPart(int partID) {

        for (Part part: allParts) {
            if(part.getId() == partID) {
                return part;
            }
        }
        return null;
    }

    /**
     *
     * @param productID look up the product id
     * @return the product id or null if there is no match
     */
    public static Product lookupProduct(int productID) {
        for (Product product: allProducts) {
            if(productID == product.getId()) {
                return product;
            }
        }
        return null;
    }

    /**
     *
     * @param partName look up part by name
     * @return the name of the part
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> found = FXCollections.observableArrayList();

        for (Part part : allParts) {
            if (part.getName().contains(partName)) {
                found.add(part);
            }
        }
        return found;
    }

    /**
     *
     * @param productName look up product by name
     * @return the name of the product
     */
    public static ObservableList<Product> lookupProduct(String productName) {

        ObservableList<Product> found = FXCollections.observableArrayList();

        for (Product prod : allProducts) {
            if (prod.getName().contains(productName)) {
                found.add(prod);
            }
        }
        return found;
    }

    /**
     *
     * @param index the index to be updated
     * @param selectedPart the part selected to be updated
     */
    public static void updatePart(int index, Part selectedPart) {

        for (Part part: getAllParts()) {

            if ((part.getId() - 1) == index) {
                allParts.set(index, selectedPart);
            }

        }
    }

    /**
     *
     * @param index the index to be updated
     * @param newProduct the product selected to be updated
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     *
     * @param selectedPart the part to be deleted
     * @return the deleted part
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     *
     * @param selectedProduct the product to be deleted
     * @return the deleted product
     */
    public static boolean deleteProduct(Product selectedProduct) {

        return allProducts.remove(selectedProduct);
    }

    /**
     *
     * @return the list of parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     *
     * @return the list of products
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
