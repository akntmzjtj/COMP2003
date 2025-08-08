package edu.curtin.addressbook;

import java.io.*;
import java.util.*;

/**
 * A simple address book application.
 *
 * @author Dave and ...
 */
public class AddressBookApp
{
    /** Used to obtain user input. */
    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args)
    {
        String fileName;

        System.out.print("Enter address book filename: ");
        fileName = input.nextLine();

        try
        {
            // Instantiate AddressBookApp object
            AddressBookApp app = new AddressBookApp();

            // create new AddressBook
            AddressBook addressBook = app.readAddressBook(fileName);
            app.showMenu(addressBook);
        }
        catch(IOException e)
        {
            System.out.println("Could not read from " + fileName + ": " + e
                .getMessage());
        }
    }

    // AddressBookApp constructor
    public AddressBookApp()
    {

    }

    /**
     * Read the address book file, containing all the names and email addresses.
     *
     * @param fileName The name of the address book file.
     * @return A new AddressBook object containing all the information.
     * @throws IOException If the file cannot be read.
     */
    public AddressBook readAddressBook(String fileName)
        throws IOException
    {
        AddressBook addressBook = new AddressBook();

        try(BufferedReader reader = new BufferedReader(new FileReader(
            fileName)))
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

    /**
     * Show the main menu, offering the user options to (1) search entries by
     * name, (2) search entries by email, or (3) quit.
     *
     * @param addressBook The AddressBook object to search.
     */
    public void showMenu(AddressBook addressBook)
    {
        boolean done = false;

        // Create a map of Options
        HashMap<Integer, Option> options = new HashMap<>();

        // Initialise map
        options.put(1, new SearchByName(addressBook));
        options.put(2, new SearchByEmail(addressBook));
        options.put(3, new SearchAll(addressBook));

        while(!done)
        {
            int optionNum;
            String searchTerm = "";
            System.out.println(
                "(1) Search by name, (2) Search by email, (3) Search all, (4) Exit");

            try
            {
                optionNum = Integer.parseInt(input.nextLine());

                // If option exists in Map container
                if(options.containsKey(optionNum))
                {
                    Option o = options.get(optionNum);
                    // Ask for search term if required
                    if(o.requiresText())
                    {
                        System.out.print("Enter search term: ");
                        searchTerm = input.nextLine();
                    }

                    // Run search
                    System.out.println(o.doOption(searchTerm));;
                }
                else if(optionNum == 4)
                {
                    done = true;
                }
                else
                {
                    System.out.println("Enter a valid number");
                }
            }
            catch(NumberFormatException e)
            {
                // The user entered something non-numerical.
                System.out.println("Enter a number");
            }
        }
    }
}
