package edu.curtin.oose.assignment2.probe.command;

import java.util.List;

public interface Command
{
    String execute();
    void printCommand(String probeName);
    String save(int sol);
    List<String> getData();
}
