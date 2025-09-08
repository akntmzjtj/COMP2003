package edu.curtin.directorymetrics;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;

import edu.curtin.directorymetrics.criteria.Criteria;
import edu.curtin.directorymetrics.criteria.CriteriaException;
import edu.curtin.directorymetrics.tree.NodeIO;
import edu.curtin.directorymetrics.tree.NodeIOException;
import edu.curtin.directorymetrics.tree.node.Node;

/**
 * Entry point into Directory Metrics application.
 */
public class DirectoryMetrics
{
    private static Logger logger = Logger.getLogger(DirectoryMetrics.class
        .getName());

    public static void main(String[] args)
    {
        String directoryPath = null;

        // Check if command-line arguments are valid
        if(args.length == 0)
        {
            directoryPath = ".";
        }
        else if(args.length == 1)
        {
            directoryPath = args[0];
        }
        else
        {
            System.out.println("Too many arguments supplied.");
            logger.info(
                () -> "Program has exited as too many arguments were supplied at command-line.");
        }

        // Create directory tree
        if(directoryPath != null)
        {
            logger.info(() -> "Path provided is valid.");

            // Read directory and store into map
            Map<String, Node> nodeMap = new HashMap<>();

            try
            {
                // true if count
                nodeMap.put("count", NodeIO.readDirectory(directoryPath, true));

                // false if show lines
                nodeMap.put("show", NodeIO.readDirectory(directoryPath, false));

                // Start app
                DirectoryMetrics app = new DirectoryMetrics();

                try(Scanner input = new Scanner(System.in);)
                {
                    // Show menu and grab input
                    app.menu(input, nodeMap);

                    logger.info(() -> "Menu now displayed.");
                }
            }
            catch(NodeIOException e)
            {
                System.out.println(e.getMessage() + " The program will exit.");

                if(logger.isLoggable(Level.WARNING))
                {
                    logger.log(Level.WARNING, "'" + directoryPath
                        + "' could not be read.", e);
                }
            }
        }
    }

    /**
     * Main menu of the application
     *
     * @param input   Scanner object to grab input from InputStream
     * @param nodeMap Map object containing the same tree but with different
     *                implementation of display function.
     */
    public void menu(Scanner input, Map<String, Node> nodeMap)
    {
        System.out.println("======== Directory Metrics ========");

        // Set default output format
        String currentOutput = "count";
        Node currentDir = nodeMap.get(currentOutput);

        // Create Criteria object
        Criteria c = new Criteria();
        c.setDefault();

        // Start the menu loop
        boolean hasExit = false;
        while(!hasExit)
        {
            System.out.println("\n Menu:");
            System.out.println(" 1. Set Criteria");
            System.out.println(" 2. Set Output");
            System.out.println(" 3. Report");
            System.out.println(" 0. Quit");
            System.out.print(" Option: ");

            String option = input.nextLine();

            switch (option)
            {
                case "1":
                    // Reset Criteria object
                    c.reset();

                    logger.info(() -> "Current Criteria object resetted.");

                    // Set new criteria
                    setCriteria(input, c);
                    break;
                case "2":
                    // Update the output style
                    currentOutput = changeOutputStyle(input, currentOutput);
                    currentDir = nodeMap.get(currentOutput);
                    break;
                case "3":
                    // Find matches and display
                    currentDir.searchMatches(c);
                    currentDir.displayMatches();

                    logger.info(() -> "Matches displayed.");
                    break;
                case "0":
                    hasExit = true;
                    break;
                case "":
                    System.out.println("Please choose an option.");
                    break;
                default:
                    System.out.println("The option " + option
                        + " chosen does not exist.");
                    break;
            }
        }
    }

    /**
     * A menu for changing the current output style.
     *
     * @param input   Scanner object to grab input from InputStream
     * @param current The current output style
     * @return the new output style
     */
    private String changeOutputStyle(Scanner input, String current)
    {
        boolean outputChosen = false;
        while(!outputChosen)
        {
            System.out.println("\n Set Output (current - " + current + "):");
            System.out.println(" 1. Count of matching files");
            System.out.println(" 2. Show actual matching lines");
            System.out.println(" 0. Back to menu");
            System.out.print(" Option: ");

            String output = input.nextLine();
            switch (output)
            {
                case "1":
                    logger.info(() -> "Output style changed to 'count'.");
                    return "count";
                case "2":
                    logger.info(() -> "Output style changed to 'show'.");
                    return "show";
                case "0":
                    return current;
                case "":
                    System.out.println("Please choose an option.");
                    break;
                default:
                    System.out.println("The output " + output
                        + " chosen does not exist.");
                    break;
            }
        }

        return null;
    }

    /**
     * Allows the user to enter regex statements.
     *
     * @param input Scanner object to grab input from InputStream.
     * @param c     Criteria object used to store new expressions.
     */
    private void setCriteria(Scanner input, Criteria c)
    {
        System.out.println("\n === Set Criteria ===");
        System.out.println(" Format: +/- t/r <the text or regex>");
        System.out.println(
            " + include, - exclude, t for plain text, r for regular expression.");
        System.out.println(" (Enter blank line to save criterions and exit)");

        boolean blankLineEntered = false;
        while(!blankLineEntered)
        {
            // Enter criterion
            System.out.print(" ==> ");
            String criterionString = input.nextLine();

            if(criterionString.isBlank())
            {
                blankLineEntered = true;

                // Check if a new set of Criteria was not added
                if(!c.canFindMatch())
                {
                    c.setDefault();
                }

                logger.info(() -> "Current Criteria object updated.");
            }
            else
            {
                // Add to Criteria object
                try
                {
                    c.addCriterion(criterionString);
                }
                catch(CriteriaException ce)
                {
                    System.out.println(ce.getMessage() + " Try again.");

                    if(logger.isLoggable(Level.WARNING))
                    {
                        logger.log(Level.WARNING, "'" + criterionString
                            + "' could not be parsed.", ce);
                    }
                }
            }
        }
    }
}
