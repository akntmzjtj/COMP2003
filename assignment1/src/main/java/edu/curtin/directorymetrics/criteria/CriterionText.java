package edu.curtin.directorymetrics.criteria;

public class CriterionText implements Criterion
{

    private String text;

    public CriterionText(String text)
    {
        this.text = text;
    }

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
