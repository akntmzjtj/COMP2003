package edu.curtin.productfinder;

import java.util.*;

/**
 * Represents any item in the catalogue, including both categories and products.
 *
 * TODO: this should form part of your Composite Pattern hierarchy.
 */
public interface CatalogueItem
{
    /**
     * Displays this catalogue item (including sub-items, if applicable).
     *
     * @param indent Indicates an amount of spacing to be output on the start of each line. For the
     * top-most item, this is likely to be "". When calling this method recursively to display
     * sub-items, you can add more spacing with 'indent + "  "', for instance.
     */
    void display(String indent);

    /**
     * Calculates the total value of the stock.
     *
     * Note: the total value of a single item is its cost times its quantity. The total value
     * of a category is the sum of all the items and subcategories within it.
     */
    double calcStockValue();

    /**
     * Finds products whose names contain a given search term, and whose price falls between
     * minPrice and maxPrice (inclusive).
     *
     * When a product is found, a String representation must be generated that includes the
     * product's name and price; e.g.: "$749.0 - PDC 230 Digital Piano". Such strings are added to
     * the 'products' list.
     *
     * @param products A list to which to add product descriptions.
     * @param searchTerm A piece of text that must appear in matching products' names.
     * @param minPrice The lowest price of matching products.
     * @param maxPrice The highest price of matching products.
     */
    void findProducts(List<String> products, String searchTerm, double minPrice, double maxPrice);


    /**
     * Convenience method for calling display() without any initial indentation.
     * (You don't need to do anything with this.)
     */
    default void display()
    {
        display("");
    }

    /**
     * Convenience method for finding products, without having a pre-existing list to put the
     * results into.
     * (You don't need to do anything with this.)
     */
    default List<String> findProducts(String searchTerm, double minPrice, double maxPrice)
    {
        var list = new ArrayList<String>();
        findProducts(list, searchTerm, minPrice, maxPrice);
        return list;
    }
}
