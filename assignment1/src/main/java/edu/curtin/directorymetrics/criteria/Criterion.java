package edu.curtin.directorymetrics.criteria;

/**
 * Interface for Criterion subclasses. Must implement a function for finding a
 * match in the given sample depending on the pattern defined at construction.
 *
 * @author Joshua Orbon 20636948
 */
public interface Criterion
{
    boolean findMatch(String sample);
}
