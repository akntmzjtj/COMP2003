package edu.curtin.oose.assignment2.probe;

import java.util.List;

import edu.curtin.oose.assignment2.probe.command.Command;

public class LowPowerState implements ProbeState
{
    @Override
    public void storeMoves(Probe probe, List<Command> moves)
    {
        // Check if commands list has any commands
        if(!probe.getCommands().isEmpty())
        {
            // TODO: throw exception
            throw new IllegalStateException("List of commands must be empty.");
        }

        // Set probes commands
        probe.setCommands(moves);

        // Update state
        probe.setState(Probe.MOVING_STATE);
    }

    @Override
    public void storeMeasure(Probe probe, List<Command> measureList)
    {
        // Check if commands list has any commands
        if(!probe.getCommands().isEmpty())
        {
            // TODO: throw exception
            throw new IllegalStateException("List of commands must be empty.");
        }

        // Set probes commands
        probe.setCommands(measureList);

        // Update state
        probe.setState(Probe.MEASURING_STATE);
    }

    @Override
    public String toString()
    {
        return "LOW POWER MODE";
    }
}
