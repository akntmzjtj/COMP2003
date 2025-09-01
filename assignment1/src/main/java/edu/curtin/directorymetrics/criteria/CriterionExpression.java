package edu.curtin.directorymetrics.criteria;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class CriterionExpression implements Criterion
{
    private Pattern p;

    public CriterionExpression(String expression)
        throws IllegalArgumentException
    {
        try
        {
            this.p = Pattern.compile(expression);
        }
        catch(PatternSyntaxException pse)
        {
            throw new IllegalArgumentException(
                "Input is not a regular expression.");
        }
    }

    public boolean findMatch(String sample)
    {
        // Create a Matcher object and pass 'sample'
        Matcher m = p.matcher(sample);

        if(m != null && m.find())
        {
            return true;
        }

        return false;
    }
}
