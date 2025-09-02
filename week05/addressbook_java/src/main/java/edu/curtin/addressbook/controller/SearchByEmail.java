package edu.curtin.addressbook.controller;

import edu.curtin.addressbook.model.AddressBook;

public class SearchByEmail implements Option
{
    private AddressBook addressBook;

    public SearchByEmail(AddressBook addressBook)
    {
        this.addressBook = addressBook;
    }

    public String doOption(String s)
    {
        return addressBook.getEntryByAddress(s);
    }

    @Override
    public Boolean requiresText()
    {
        return true;
    }
}
