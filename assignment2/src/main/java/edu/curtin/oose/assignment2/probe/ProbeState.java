package edu.curtin.oose.assignment2.probe;

import java.util.List;

import edu.curtin.oose.assignment2.probe.command.Command;

public interface ProbeState
{
    String getState();
    void storeMoves(Probe probe, List<Command> moves);
    void storeMeasure(Probe probe, List<Command> measureList);
}
