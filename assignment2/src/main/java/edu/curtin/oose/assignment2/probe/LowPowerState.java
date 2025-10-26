package edu.curtin.oose.assignment2.probe;

import java.util.List;

import edu.curtin.oose.assignment2.probe.command.Command;

/**
 * Implementation of ProbeState interface.
 *
 * @author Joshua Orbon 20636948
 */
public class LowPowerState implements ProbeState
{
    /**
     * Handles how the List of move commands are stored.
     *
     * @param probe The Probe object that has delegated the task to the state
     * @param moves List of Command objects
     * @throws IllegalStateException When probe still has instructions to
     *                               execute
     */
    @Override
    public void storeMoves(Probe probe, List<Command> moves)
    {
        // Check if commands list has any commands
        if(!probe.getCommands().isEmpty())
        {
            throw new IllegalStateException("List of commands must be empty.");
        }

        // Set probes commands
        probe.setCommands(moves);

        // Update state
        probe.setState(Probe.MOVING_STATE);
    }

    /**
     * Handles how the List of measure commands are stored.
     *
     * @param probe       The Probe object that has delegated the task to the
     *                    state
     * @param measureList List of Command objects
     * @throws IllegalStateException When probe still has instructions to
     *                               execute
     */
    @Override
    public void storeMeasure(Probe probe, List<Command> measureList)
    {
        // Check if commands list has any commands
        if(!probe.getCommands().isEmpty())
        {
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
