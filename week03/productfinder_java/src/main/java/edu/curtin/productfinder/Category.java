package edu.curtin.productfinder;

import java.util.LinkedList;
import java.util.List;

public class Category implements CatalogueItem
{
    private final static String INDENT = "   ";
    private String name;
    private List<CatalogueItem> children;

    public Category(String name)
    {
        this.name = name;
        this.children = new LinkedList<>();
    }

    @Override
    public void display(String indent)
    {
        System.out.println(indent + this.name + ":");

        // Then recurse into node's children nodes
        for(CatalogueItem item : children)
        {
            item.display(indent + INDENT);
        }
    }

    @Override
    public double calcStockValue()
    {
        double total = 0.0;
        for(CatalogueItem item : children)
        {
            total += item.calcStockValue();
        }

        return total;
    }

    @Override
    public void findProducts(
        List<String> products, String searchTerm, double minPrice,
        double maxPrice
    )
    {
        for(CatalogueItem item : children)
        {
            item.findProducts(products, searchTerm, minPrice, maxPrice);
        }
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void addItem(CatalogueItem item)
    {
        this.children.add(item);
    }

    public void removeItem(CatalogueItem item)
    {
        this.children.remove(item);
    }

    // @Override
    // public CatalogueItem findItem(String name)
    // {
    //     CatalogueItem found = null;
    //     if(this.name.equals(name))
    //     {
    //         found = this;
    //     }
    //     else
    //     {
    //         for(CatalogueItem item : children)
    //         {
    //             found = item.findItem(name);
    //             if(found != null)
    //             {
    //                 return found;
    //             }
    //         }
    //     }

    //     return found;
    // }
}
