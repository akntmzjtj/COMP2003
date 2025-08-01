package edu.curtin.addressbook;

import java.util.*;

/**
 * Contains all the address book entries.
 *
 * @author ...
 */
public class AddressBook
{
    private HashMap<String, Entry> entries;

    public AddressBook()
    {
        this.entries = new HashMap<String, Entry>();
    }

    public void addEntry(String name, Entry entry)
    {
        entries.put(name, entry);
    }

    public Entry getEntryByName(String name)
    {
        return entries.get(name);
    }

    public Entry getEntryByAddress(String address)
    {
        for(var kv : entries.entrySet())
        {
            String name = kv.getKey();
            Entry entry = kv.getValue();

            if(entry.containsAddress(address))
            {
                return entries.get(name);
            }
        }

        return null;
    }

    public String toString()
    {
        return entries.toString();
    }
}
