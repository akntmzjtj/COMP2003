package edu.curtin.directorymetrics;

import java.util.LinkedList;
import java.util.List;

public interface Node
{
    int getMatchesCount();

    void setMatchesCount(int matchesCount);

    void searchMatches(Criteria c);

    void displayMatches(String indent);

    default void displayMatches()
    {
        displayMatches("");
    }
}
