package edu.curtin.productfinder;

import java.util.List;

public class Product implements CatalogueItem
{
    private String name;
    private double price;
    private int quantity;

    public Product(String name, double price, int quantity)
    {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public void display(String indent)
    {
        System.out.print(indent + this.name + "; $");
        System.out.printf("%.2f", this.price);
        System.out.println("; x" + this.quantity);
    }

    @Override
    public double calcStockValue()
    {
        return this.quantity * this.price;
    }

    @Override
    public void findProducts(
        List<String> products, String searchTerm, double minPrice,
        double maxPrice
    )
    {
        if(name.contains(searchTerm))
            if(this.price >= minPrice && this.price <= maxPrice)
            {
                String out = String.format("$%.2f -- %s", this.price, this.name);

                products.add(out);
            }
    }

    public String getName()
    {
        return this.name;
    }

    public double getPrice()
    {
        return this.price;
    }

    public int getQuantity()
    {
        return this.quantity;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    // @Override
    // public CatalogueItem findItem(String name)
    // {
    // CatalogueItem found = null;
    // if(this.name.equals(name))
    // {
    // found = this;
    // }

    // return found;
    // }
}
