package edu.curtin.addressbook.model;

import java.util.*;

/**
 * Represents a single address book entry.
 *
 * @author ...
 */
public class Entry
{
    // Insert your code here.
    private String name;
    private HashSet<String> addresses;

    public Entry(String name, String... addresses)
    {
        this.name = name;
        this.addresses = new HashSet<String>();

        for(int i = 0; i < addresses.length; i++)
        {
            this.addresses.add(addresses[i]);
        }
    }

    public boolean containsAddress(String address) {
        return addresses.contains(address);
    }

    public String toString()
    {
        return this.name + " " + this.addresses.toString();
    }
}
