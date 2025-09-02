package edu.curtin.matheval;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Parses mathematical expressions, and builds a tree of ExprNode objects to
 * represent the parsed expression.
 */
public class ExprParser
{
    // Initialise Logger object
    private static final Logger logger = Logger.getLogger(ExprParser.class
        .getName());

    // The regex pattern used for tokenisation purposes. Basically, this skips
    // any amount of
    // whitespace, then checks for a fractional number (containing and possibly
    // starting with "."),
    // then checks for an integer, and finally falls back to a single other
    // character of any type.
    private static final Pattern TOKEN = Pattern.compile(
        "\\s*([0-9]*\\.[0-9]+|[0-9]+|.)");

    public ExprParser()
    {
    }

    /**
     * Parses a string, which is assumed to contain a mathematical expression.
     * Returns the root node of the resulting object tree.
     */
    public ExprNode parse(String s)
    {
        List<String> tokens = new LinkedList<>();
        logger.info(() -> "tokens List initialised.");

        // Tokenise the string, by repeatedly applying the 'TOKEN' regular
        // expression until it
        // doesn't match anymore (which should only happen at the end of the
        // string).
        String substr = s;
        boolean done = false;
        do
        {
            Matcher matcher = TOKEN.matcher(substr);
            if(matcher.lookingAt())
            {
                tokens.add(matcher.group(1));
                substr = substr.substring(matcher.end());
            }
            else
            {
                done = true;
            }
        } while(!done);

        // Invoke the actual parsing logic.
        return parseAdd(tokens);
    }

    /**
     * Parses a sequence of zero-or-more "+" / "-" operators (the lowest
     * operator precedence level).
     */
    private ExprNode parseAdd(List<String> tokens)
    {
        logger.info(() -> "Entered with tokens " + tokens.toString());

        logger.info(() -> "Call parseMul() with tokens " + tokens.toString());
        ExprNode node = parseMul(tokens);

        boolean end = false;
        while(!end && !tokens.isEmpty())
        {
            // Expect next token to be '+' or '-'
            String token = tokens.remove(0);
            switch (token)
            {
                case "+":
                    logger.info(
                        () -> "case \"+\" entered. Return new AddOperator object.");

                    node = new AddOperator(node, parseMul(tokens));
                    break;

                case "-":
                    logger.info(
                        () -> "case \"-\" entered. Return new SubOperator object.");
                    node = new SubOperator(node, parseMul(tokens));
                    break;

                default:
                    // The next token isn't "+" or "-", which means we assume
                    // this additive sequence is over, so push the token back
                    // onto the list, and end.
                    logger.info(
                        () -> "default: entered. Push token back onto the list and end.");

                    tokens.add(0, token);
                    end = true;
                    break;
            }
        }
        return node;
    }

    /**
     * Parses a sequence of zero-or-more "*" / "/" operators.
     */
    private ExprNode parseMul(List<String> tokens)
    {
        logger.info(() -> "Entered with tokens " + tokens.toString());

        logger.info(() -> "Call parsePrimary() with tokens " + tokens.toString());
        ExprNode node = parsePrimary(tokens);

        boolean end = false;
        while(!end && !tokens.isEmpty())
        {
            // Expect next token to be "*" or "/"
            String token = tokens.remove(0);
            switch (token)
            {
                case "*":
                    logger.info(
                        () -> "case \"*\" entered. Return new MulOperator object.");

                    node = new MulOperator(node, parsePrimary(tokens));
                    break;

                case "/":
                    logger.info(
                        () -> "case \"/\" entered. Return new DivOperator object.");

                    node = new DivOperator(node, parsePrimary(tokens));
                    break;

                default:
                    // The next token isn't "*" or "/", which means we assume
                    // this multiplicative
                    // sequence is over, so push the token back onto the list,
                    // and end.
                    logger.info(
                        () -> "default: entered. Push token back onto the list and end.");
                    tokens.add(0, token);
                    end = true;
                    break;
            }
        }
        return node;
    }

    /**
     * Parses a "primary" value, which can be either a sub-expression in
     * brackets, a negation "-" operator, a reference to the "x" variable, or a
     * literal number.
     */
    private ExprNode parsePrimary(List<String> tokens)
    {
        logger.info(() -> "Entered with tokens " + tokens.toString());
        ExprNode node;
        String token = tokens.remove(0); // Obtain the next token
        switch (token)
        {
            case "(":
                logger.info(
                    () -> "case \"(\" entered. Sub-expression inside brackets. Get return of parseAdd()");

                // Sub-expression inside brackets.
                node = parseAdd(tokens);
                tokens.remove(0); // Remove closing ")"
                break;

            case "-":
                logger.info(
                    () -> "case \"-\" entered. Return new NegationOperator object using return of parsePrimary().");

                // Inverted value (e.g., -(x+1))
                node = new NegationOperator(parsePrimary(tokens));
                break;

            case "x":
                logger.info(
                    () -> "case \"x\" entered. New XValue object returned.");

                // Variable value
                node = new XValue();
                break;

            default:
                logger.info(
                    () -> "default entered. Token is literal value. Parse and return new Value object.");

                // Literal number
                node = new Value(Double.parseDouble(token));
                break;
        }
        return node;
    }
}
