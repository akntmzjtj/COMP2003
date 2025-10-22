package edu.curtin.oose.assignment2.probe.command;

import java.util.List;

import edu.curtin.oose.assignment2.probe.Probe;

public interface Command
{
    void execute(Probe probe);

    void printCommand(String probeName);

    String save(int sol);

    List<String> getData();
}
