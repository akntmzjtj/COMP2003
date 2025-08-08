package edu.curtin.addressbook;

public class SearchAll implements Option
{
    private AddressBook addressBook;

    public SearchAll(AddressBook addressBook)
    {
        this.addressBook = addressBook;
    }

    @Override
    public String doOption(String s)
    {
        return addressBook.getAllEntries();
    };

    @Override
    public Boolean requiresText()
    {
        return false;
    }
}
