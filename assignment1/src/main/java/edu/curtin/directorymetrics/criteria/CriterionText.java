package edu.curtin.directorymetrics.criteria;

/**
 * A class that implements Criterion. Just stores plain text to be checked
 * against.
 *
 * @author Joshua Orbon 20636948
 */
public class CriterionText implements Criterion
{

    private String text;

    /**
     * Constructor for CriterionText class.
     *
     * @param text Text used to be checked again.
     * @throws IllegalArgumentException when the string is blank
     */
    public CriterionText(String text)
    {
        if(text.isBlank())
        {
            throw new IllegalArgumentException("Text is empty.");
        }

        this.text = text;
    }

    /**
     * Checks if the contents of the line contains the string defined at
     * construction.
     *
     * @param sample Contains line from a file
     */
    @Override
    public boolean findMatch(String sample)
    {
        if(sample.contains(text))
        {
            return true;
        }

        return false;
    }
}
