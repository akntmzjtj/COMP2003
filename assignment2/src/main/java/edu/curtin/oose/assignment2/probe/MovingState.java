package edu.curtin.oose.assignment2.probe;

import java.util.List;

import edu.curtin.oose.assignment2.probe.command.Command;

public class MovingState implements ProbeState
{
    @Override
    public void storeMoves(Probe probe, List<Command> moves)
    {
        // Set probe's commands
        probe.setCommands(moves);
    }

    @Override
    public void storeMeasure(Probe probe, List<Command> measureList)
    {
        // Stop moving and store commands
        probe.setCommands(measureList);

        // Update state
        probe.setState(Probe.MEASURING_STATE);
    }

    @Override
    public String toString()
    {
        return "MOVING";
    }
}
