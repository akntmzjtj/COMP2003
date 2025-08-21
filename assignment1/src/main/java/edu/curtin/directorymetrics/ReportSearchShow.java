package edu.curtin.directorymetrics;

public class ReportSearchShow implements ReportSearch
{
    public void printReport(LineMatch[] matches, String indent)
    {
        System.out.println();
        for(int i = 0; i < matches.length; i++)
        {
            System.out.printf("%s%d %s\n", indent, matches[i].getLineNumber(),
                matches[i].getContent());
        }
    }
}
