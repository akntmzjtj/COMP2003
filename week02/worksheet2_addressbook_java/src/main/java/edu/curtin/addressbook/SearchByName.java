package edu.curtin.addressbook;

public class SearchByName implements Option
{
    private AddressBook addressBook;

    public SearchByName(AddressBook addressBook)
    {
        this.addressBook = addressBook;
    }

    @Override
    public String doOption(String s)
    {
        return addressBook.getEntryByName(s);
    };

    @Override
    public Boolean requiresText()
    {
        return true;
    }
}
