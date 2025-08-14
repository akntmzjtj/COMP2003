package edu.curtin.productfinder;

import java.io.*;
import java.util.*;

/**
 * Entry point into the Product Finder app. This reads the catalogue file, then displays a console
 * menu for invoking various operations on the tree of products and categories.
 */
public class ProductFinder
{
    public static void main(String[] args)
    {
        new ProductFinder().menu(new CatalogueFileIO());
    }

    public void menu(CatalogueFileIO catalogue)
    {
        try
        {
            CatalogueItem rootItem = catalogue.readCatalogue(CatalogueFileIO.CATALOGUE_FILE);

            System.out.println("Product Finder\n--------------");
            Scanner sc = new Scanner(System.in);

            boolean done = false;
            while(!done)
            {
                System.out.print(
                    "\nChoose - (x) exit, (d) display, (v) calc stock value, (f) find products: ");
                String opt = sc.nextLine();
                switch(opt)
                {
                    case "d":
                        rootItem.display();
                        break;

                    case "v":
                        System.out.printf("Total value is $%.2f.\n", rootItem.calcStockValue());
                        break;

                    case "f":
                        System.out.print("Enter a search term (or leave blank): ");
                        String searchTerm = sc.nextLine();

                        System.out.print("Enter a minimum price (or leave blank): ");
                        double minPrice = readDouble(sc, 0.0);

                        System.out.print("Enter a maximum price (or leave blank): ");
                        double maxPrice = readDouble(sc, Double.MAX_VALUE);

                        boolean anyFound = false;
                        System.out.println("Matching products:");
                        for(String label : rootItem.findProducts(searchTerm, minPrice, maxPrice))
                        {
                            anyFound = true;
                            System.out.println("  " + label);
                        }
                        if(!anyFound)
                        {
                            System.out.println("  [none found]");
                        }
                        break;

                    case "x":
                        done = true;
                        break;

                    default:
                        System.out.printf("Unknown option '%s'.\n", opt);
                }
            }
        }
        catch(CatalogueFormatException e)
        {
            System.out.println("Parsing error in catalogue file: " + e.getMessage());
        }
        catch(IOException e)
        {
            System.out.println("IO error reading catalogue file: " + e.getMessage());
        }
    }

    /**
     * Reads a double from the console and returns it, or a default value if no valid number is
     * entered.
     */
    private static double readDouble(Scanner sc, double defaultValue)
    {
        try
        {
            return Double.parseDouble(sc.nextLine());
        }
        catch(NumberFormatException e)
        {
            return defaultValue;
        }
    }
}
