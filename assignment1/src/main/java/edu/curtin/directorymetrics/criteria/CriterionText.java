package edu.curtin.directorymetrics.criteria;

/**
 * A class that implements Criterion. Just stores plain text to be checked
 * against.
 */
public class CriterionText implements Criterion
{

    private String text;

    /**
     * Constructor for CriterionText class.
     *
     * @param text Text used to be checked again.
     */
    public CriterionText(String text)
    {
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
