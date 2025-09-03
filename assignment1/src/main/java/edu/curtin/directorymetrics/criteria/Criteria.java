package edu.curtin.directorymetrics.criteria;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.regex.PatternSyntaxException;

/**
 * Creates an object that can manage inclusions and exclusions for filtering
 * text files. The criterions can be added in the specified form.
 */
public class Criteria
{
    private static final Logger logger = Logger.getLogger(Criteria.class
        .getName());

    private Queue<Criterion> inclusions;
    private Queue<Criterion> exclusions;

    /**
     * Constructs a Criteria object with empty inclusion and exclusion lists.
     */
    public Criteria()
    {
        this.inclusions = new LinkedList<>();
        this.exclusions = new LinkedList<>();

        logger.info(() -> "Criteria object constructed.");
    }

    /**
     * Removes all added inclusion and exclusion criterions
     */
    public void reset()
    {
        this.inclusions.clear();
        this.exclusions.clear();
    }

    /**
     * Resets the object and adds an inclusion criterion that will include all
     * lines of a file
     */
    public void setDefault()
    {
        reset();

        // Add regex that includes all lines
        this.inclusions.add(new CriterionExpression(".*"));
    }

    /**
     * Checks if the inclusion and exclusion lists are empty
     * @return true when at least one container has a criterion
     */
    public boolean canFindMatch()
    {
        return !this.inclusions.isEmpty() || !this.exclusions.isEmpty();
    }

    /**
     * Adds a criterion to the inclusion or exclusion list. The input must be in
     * the format: "+/- r/t <pattern>".
     *
     * @param input The criterion to be added.
     * @throws CriteriaException if the format is invalid.
     */
    public void addCriterion(String input) throws CriteriaException
    {
        // Parse input (+/- r/t regex/text)
        String[] inputArray = input.split(" ", 3);

        // Throw exception when array formed does not match specified format
        if(inputArray.length != 3)
        {
            throw new CriteriaException(
                "Criterion provided does not match format.");
        }

        // inputArray[0] must either be '+' or '-'
        // Throw exception when first two elements are not valid
        if(!inputArray[0].equals("+") && !inputArray[0].equals("-"))
        {
            throw new CriteriaException("First argument must be '+' or '-'.");
        }

        // inputArray[1] must either be 't' or 'r'
        if(!inputArray[1].equals("t") && !inputArray[1].equals("r"))
        {
            throw new CriteriaException("Second argument must be 't' or 'r'.");
        }

        // inputArray[2] is blank (does not provide pattern/text)
        if(inputArray[2].isBlank())
        {
            throw new CriteriaException("Third argument cannot be empty.");
        }

        // Check if the user provided a regular expression
        Criterion c;
        if(inputArray[1].equals("r"))
        {
            try
            {
                c = new CriterionExpression(inputArray[2]);
            }
            catch(PatternSyntaxException pse)
            {
                throw new CriteriaException(
                    "Input could not be compiled into a Pattern object.", pse);
            }
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

        logger.info(() -> "'" + input + "' added.");
    }

    /**
     * Checks all lines in a file against the given inclusion and exclusion
     * patterns
     *
     * @param file to be checked.
     * @return An array of LineMatch objects representing matching lines, or
     *         null if an error occurs.
     */
    public LineMatch[] findMatchInFile(File file)
    {
        // Throw exception when both inclusions and exclusions list are empty
        if(!canFindMatch())
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
            logger.warning(() -> "Failed to read '" + file.getName() + "'");
        }

        return null;
    }

    /**
     * Checks if any of the given criteria match the provided line.
     *
     * @param criterions The queue of Criterion objects to check.
     * @param line       The line of text to test.
     * @return true if any criterion matches the line, false otherwise.
     */
    private boolean checkMatch(Queue<Criterion> criterions, String line)
    {
        for(Criterion cri : criterions)
        {
            // Check if there are any matches
            if(cri.findMatch(line))
            {
                return true;
            }
        }

        return false;
    }
}
