package edu.curtin.directorymetrics.criteria;

/**
 * Interface for Criterion subclasses. Must implement a function for finding a
 * match in the given sample depending on the pattern defined at construction.
 */
public interface Criterion
{
    boolean findMatch(String sample);
}
