package edu.curtin.productfinder;

import java.io.*;
import java.util.*;

/**
 * Reads a catalogue file (consisting of categories and products), and returns the root category
 * in the tree.
 *
 * TODO: complete the code to build a Composite Pattern tree structure containing all the
 * categories and products.
 *
 * Each non-blank line in the file represents either a category or a product. In either case, it
 * is divided into fields separated by ':':
 *
 * - The 1st field is the _parent_ category, which must have been defined on a preceding line, or
 *   be the root "All" category (which exists before the file is read). This is the category into
 *   which the current item needs to be added.
 *
 * - The 2nd field is the name of the current item. (If it's a category, then subsequent lines may
 *   refer back to this name.)
 *
 * - If the line represents a category, there are no further fields.
 *
 * - If the line represents a product, the 3rd field contains the product's price, and the 4th
 *   field contains the quantity in stock.
 */
public class CatalogueFileIO
{
    public static final String CATALOGUE_FILE = "catalogue.txt";

    public CatalogueItem readCatalogue(String filename) throws CatalogueFormatException,
                                                               IOException
    {
        // TODO: Create the root category
        // ...

        try(var reader = new BufferedReader(new FileReader(filename)))
        {
            String line = reader.readLine();
            while(line != null)
            {
                line = line.strip();
                if(!line.isEmpty())
                {
                    String[] parts = line.split(":");
                    String parentCategoryName = parts[0];
                    String name;

                    // TODO: Find the named parent category (and see if it's valid)
                    // ...

                    switch(parts.length)
                    {
                        case 2:  // Category
                            name = parts[1];

                            // TODO: Create new subcategory
                            // ...
                            break;

                        case 4:  // Product
                            name = parts[1];
                            try
                            {
                                double price = Double.parseDouble(parts[2]);
                                int quantityInStock = Integer.parseInt(parts[3]);

                                // TODO: Create new product
                                // ...
                            }
                            catch(NumberFormatException e)
                            {
                                throw new CatalogueFormatException("Catalogue product: invalid number format", e);
                            }
                            break;

                        default:
                            throw new CatalogueFormatException("Unknown line format");
                    }
                }
                line = reader.readLine();
            }
        }

        // TODO: return the root category
        // return ...
        return null;
    }
}
