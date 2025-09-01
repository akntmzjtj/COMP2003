package edu.curtin.directorymetrics.criteria;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Criteria
{
    private Queue<Criterion> inclusions;
    private Queue<Criterion> exclusions;

    public Criteria()
    {
        this.inclusions = new LinkedList<>();
        this.exclusions = new LinkedList<>();
    }

    public Criteria(Criteria c)
    {
        this.inclusions = c.getInclusions();
        this.exclusions = c.getExclusions();
    }

    public Queue<Criterion> getInclusions()
    {
        return this.inclusions;
    }

    public Queue<Criterion> getExclusions()
    {
        return this.exclusions;
    }

    public void addCriterion(String input) throws IllegalArgumentException
    {
        // Parse input (+/- r/t regex/text)
        String[] inputArray = input.split(" ", 3);

        // Throw exception when array formed does not match specified format
        if(inputArray.length != 3)
        {
            throw new IllegalArgumentException(
                "Criterion provided does not match format.");
        }

        // inputArray[0] must either be '+' or '-'
        // Throw exception when first two elements are not valid
        if(!inputArray[0].equals("+") && !inputArray[0].equals("-"))
        {
            throw new IllegalArgumentException(
                "First argument must be '+' or '-'.");
        }

        // inputArray[1] must either be 't' or 'r'
        if(!inputArray[1].equals("t") && !inputArray[1].equals("r"))
        {
            throw new IllegalArgumentException(
                "Second argument must be 't' or 'r'.");
        }

        // Create Criterion object
        Criterion c = null;

        // Check if the user provided a regular expression
        if(inputArray[1].equals("r"))
        {
            c = new CriterionExpression(inputArray[2]);
        }
        else // if inputArray[1] is 't' as header guard filters other values
        {
            c = new CriterionText(inputArray[2]);
        }

        // Add to criterias list
        if(inputArray[0].equals("+"))
        {
            this.inclusions.add(c);
        }
        else // if inputArray[0] is '-' as header guard filters other values
        {
            this.exclusions.add(c);
        }
    }

    public LineMatch[] findMatchInFile(File file)
        throws IllegalArgumentException
    {
        // Throw exception when both inclusions and exclusions list are empty
        if(this.inclusions == null && this.exclusions == null)
        {
            throw new IllegalStateException(
                "No criterions to be checked against.");
        }

        List<LineMatch> matches = new LinkedList<>();

        try(Scanner scFile = new Scanner(file))
        {
            int lineNum = 1;
            while(scFile.hasNextLine())
            {
                String line = scFile.nextLine();

                // First test all exclusion criteria
                // If any match found, ignore line
                if(!checkMatch(this.exclusions, line))
                {
                    // Then test all inclusion criteria
                    if(checkMatch(this.inclusions, line))
                    {
                        matches.add(new LineMatch(lineNum, line));
                    }
                }

                // Increment after line has been read
                lineNum++;
            }

            // Convert list to array
            return matches.toArray(new LineMatch[0]);
        }
        catch(IOException io)
        {
            System.out.println("Failed to read provided file. " + io
                .getMessage());
        }

        return null;
    }

    private boolean checkMatch(Queue<Criterion> criterions, String line)
    {
        Iterator<Criterion> it = criterions.iterator();
        while(it.hasNext())
        {
            Criterion c = it.next();

            // Check if there are any matches
            if(c.findMatch(line))
            {
                return true;
            }
        }

        return false;
    }
}
