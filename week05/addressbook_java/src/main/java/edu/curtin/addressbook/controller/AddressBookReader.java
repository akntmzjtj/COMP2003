package edu.curtin.addressbook.controller;

import edu.curtin.addressbook.model.AddressBook;
import edu.curtin.addressbook.model.Entry;

import java.util.Arrays;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AddressBookReader
{
    public AddressBookReader()
    {
    }

    /**
     * Read the address book file, containing all the names and email addresses.
     *
     * @param fileName The name of the address book file.
     * @return A new AddressBook object containing all the information.
     * @throws IOException If the file cannot be read.
     */
    public AddressBook readAddressBook(String filename) throws IOException
    {
        AddressBook addressBook = new AddressBook();

        try(BufferedReader reader = new BufferedReader(new FileReader(
            filename)))
        {
            String line = reader.readLine();
            while(line != null)
            {
                String[] parts = line.split(":");

                // Note:
                // parts[0] contains the person's name.
                // parts[1], parts[2], etc. contain the person's email
                // address(es).

                line = reader.readLine();

                // Create Entry object
                Entry temp = new Entry(parts[0], Arrays.copyOfRange(parts, 1,
                    parts.length));

                // Add entry into AddressBook
                addressBook.addEntry(parts[0], temp);
            }
        }

        return addressBook;
    }
}
