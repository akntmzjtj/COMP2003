package edu.curtin.directorymetrics.criteria;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * A class that implements Criterion. Uses Pattern and Matcher to find a match
 * in a given string (a line from a file). The pattern (regex) is defined at the
 * construction of the object.
 *
 * @author Joshua Orbon 20636948
 */
public class CriterionExpression implements Criterion
{
    private Pattern p;

    /**
     * Contructs object by using regex defined by expression
     *
     * @param expression the string that contains the regex
     * @throws PatternSyntaxException   when Pattern object cannot compile given
     *                                  string
     * @throws IllegalArgumentException when string is blank
     */
    public CriterionExpression(String expression)
    {
        if(expression.isBlank())
        {
            throw new IllegalArgumentException("Expression is empty.");
        }

        this.p = Pattern.compile(expression);
    }

    /**
     * Checks if the contents of the line matches the pattern defined at
     * construction.
     *
     * @param sample Contains line from a file
     */
    @Override
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
