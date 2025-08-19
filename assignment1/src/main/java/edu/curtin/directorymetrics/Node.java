package edu.curtin.directorymetrics;

public interface Node
{
    void display(String indent);

    // Move this method into another class for use of either Strategy/Template
    // pattern
    default void display()
    {
        display("");
    }
}
