package edu.curtin.matheval;

import java.util.Scanner;

/**
 * Entry point for out mathematical expression app.
 */
public class Main
{
    public static void main(String[] args)
    {
        System.out.print("Enter a mathematical expression: ");
        String expression;
        try(Scanner sc = new Scanner(System.in))
        {
            expression = sc.nextLine();
        }
        ExprNode exprRoot = new ExprParser().parse(expression);
            
        System.out.printf("Parsed expression: '%s'\nEvaluating for different values of x...\n", exprRoot.toString());
        for(double x = 0.0; x <= 10.00001; x += 0.5)
        {
            System.out.printf("(%.2f, %.2f)\n", x, exprRoot.evaluate(x));
        }
    }
}
