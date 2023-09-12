package main.task1_dariust;

import javafx.collections.ObservableList;

/**
 *
 * @author Darius Taylor
 */

public class Product {
    private ObservableList<Part> associatedParts;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(ObservableList<Part> associatedParts, int id, String name, double price, int stock, int min, int max) {
        this.associatedParts = associatedParts;
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id set the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name set the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return the product price
     */
    public double getPrice() {
        return price;
    }

    /**
     *
     * @param price set the price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     *
     * @return product stock/inventory#
     */
    public int getStock() {
        return stock;
    }

    /**
     *
     * @param stock set the stock/inventory#
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     *
     * @return the minimum
     */
    public int getMin() {
        return min;
    }

    /**
     *
     * @param min set the minimum
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     *
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     *
     * @param max set the max
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     *
     * @param part add the part to the associated list
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     *
     * @param selectedAssociatedPart delete the selected part
     * @return the deleted part
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        return associatedParts.remove(selectedAssociatedPart);
    }

    /**
     *
     * @return the associated parts observable list
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

}
