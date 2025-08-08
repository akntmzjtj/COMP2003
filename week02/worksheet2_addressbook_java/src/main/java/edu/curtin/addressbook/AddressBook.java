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

    public String getEntryByName(String name)
    {
        return entries.get(name).toString();
    }

    public String getEntryByAddress(String address)
    {
        for(var kv : entries.entrySet())
        {
            String name = kv.getKey();
            Entry entry = kv.getValue();

            if(entry.containsAddress(address))
            {
                return entries.get(name).toString();
            }
        }

        return null;
    }

    public String getAllEntries()
    {
        String out = "";
        Entry entryString[] = new Entry[entries.size()];

        int j = 0;
        for(Entry e : entries.values())
        {
            entryString[j] = e;
            j++;
        }

        for(int i = 0; i < entryString.length - 1; i++)
        {
            out += entryString[i].toString() + "\n";
        }

        out += entryString[entryString.length - 1];

        return out;
    }

    public String toString()
    {
        return entries.toString();
    }
}
