package edu.curtin.directorymetrics;

import java.util.Scanner;

/**
 * Entry point into the application. To change the package, and/or the name of
 * this class, make sure to update the 'mainClass = ...' line in build.gradle.
 */
public class DirectoryMetrics
{
    public static void main(String[] args)
    {
        new DirectoryMetrics().menu();
    }

    public void menu()
    {
        try(Scanner input = new Scanner(System.in))
        {
            System.out.println("======== Directory Metrics ========");

            // boolean hasExit = false;
            // while(!hasExit)
            // {
            //     System.out.println("\n    Menu:");
            //     System.out.println("    1. Set Criteria");
            //     System.out.println("    2. Set Output");
            //     System.out.println("    3. Report");
            //     System.out.println("    0. Quit");
            //     System.out.print("    Option: ");

            //     String option = input.nextLine();

            //     switch (option)
            //     {
            //         case "1":
            //             break;
            //         case "2":
            //             break;
            //         case "3":
            //             break;
            //         case "0":
            //             hasExit = true;
            //             break;
            //         default:
            //             System.out.println("The option " + option
            //                 + " chosen does not exist.");
            //             break;
            //     }

            // }
            //
            // DEBUG
            DirectoryIO directoryIO = new DirectoryIO();
            Node root = directoryIO.readDirectory(
                "/Users/joshuaorbon/Desktop/COMP2003/assignment1", input);

            root.display();
        }
    }
}
