package edu.curtin.directorymetrics;

public interface Node
{
    void searchMatches(
        String indent, String pathname, Criteria c, ReportSearch r
    );

    default void searchMatches(Criteria c, ReportSearch r)
    {
        searchMatches("", "", c, r);
    }
}
