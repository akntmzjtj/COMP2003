package edu.curtin.directorymetrics;

import java.util.Stack;

public interface Node
{
    void searchMatches(
        String indent, Stack<String> path, Criteria c, ReportSearch r
    );

    default void searchMatches(Criteria c, ReportSearch r)
    {
        searchMatches("", new Stack<>(), c, r);
    }
}
