package edu.curtin.directorymetrics;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import edu.curtin.directorymetrics.criteria.Criteria;
import edu.curtin.directorymetrics.node.DirectoryIO;
import edu.curtin.directorymetrics.node.Node;

/**
 * Entry point into the application. To change the package, and/or the name of
 * this class, make sure to update the 'mainClass = ...' line in build.gradle.
 */
public class DirectoryMetrics
{
    public static void main(String[] args)
    {
        String directoryPath = null;

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
        }

        if(directoryPath != null)
        {
            // Read directory and store into map
            Map<String, Node> nodeMap = new HashMap<>();

            DirectoryIO nodeIO = new DirectoryIO();
            // true if count
            nodeMap.put("count", nodeIO.readDirectory(directoryPath, true));

            // false if show lines
            nodeMap.put("show", nodeIO.readDirectory(directoryPath, false));

            // Start app
            DirectoryMetrics app = new DirectoryMetrics();
            try(Scanner input = new Scanner(System.in);)
            {
                // Show menu and grab input
                app.menu(input, nodeMap);
            }
        }
    }

    public void menu(Scanner input, Map<String, Node> nodeMap)
    {
        System.out.println("======== Directory Metrics ========");

        String currentOutput = "count";
        Node currentDir = nodeMap.get(currentOutput);

        Criteria c = new Criteria();
        c.addCriterion("+ r .*"); // default criterion

        // DEBUG
        // c.addCriterion("+ t Hello");
        // c.addCriterion("+ t public");
        // c.addCriterion("+ r abstract|interface");
        // c.addCriterion("+ r (abc");
        // c.addCriterion("* t //");

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
                    // create new Criteria object
                    c = new Criteria();

                    // set criteria
                    setCriteria(input, c);
                    break;
                case "2":
                    currentOutput = setOutput(input, currentOutput);
                    currentDir = nodeMap.get(currentOutput);
                    break;
                case "3":
                    currentDir.searchMatches(c);
                    currentDir.displayMatches();
                    break;
                case "0":
                    hasExit = true;
                    break;
                default:
                    System.out.println("The option " + option
                        + " chosen does not exist.");
                    break;
            }
        }
    }

    private String setOutput(Scanner input, String current)
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
                    return "count";
                case "2":
                    return "show";
                case "0":
                    return current;
                default:
                    System.out.println("The output " + output
                        + " chosen does not exist.");
                    break;
            }
        }

        return null;
    }

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
            }
            else
            {
                // Add to Criteria object
                c.addCriterion(criterionString);
            }
        }
    }
}
