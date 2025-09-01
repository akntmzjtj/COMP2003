package edu.curtin.directorymetrics.node;

import edu.curtin.directorymetrics.criteria.Criteria;

public interface Node
{
    int getMatchesCount();

    void searchMatches(Criteria c);

    void displayMatches(String indent);

    default void displayMatches()
    {
        displayMatches("");
    }
}
