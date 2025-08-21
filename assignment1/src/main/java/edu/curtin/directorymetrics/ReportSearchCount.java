package edu.curtin.directorymetrics;

public class ReportSearchCount implements ReportSearch
{
    public void printReport(LineMatch[] matches, String indent)
    {
        String plural = "line";

        if(matches.length > 1)
        {
            plural += "s";
        }

        System.out.printf(" %d %s\n", matches.length, plural);
    }
}
