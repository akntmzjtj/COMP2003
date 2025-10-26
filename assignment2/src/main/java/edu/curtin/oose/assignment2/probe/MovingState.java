package edu.curtin.oose.assignment2.probe;

import java.util.List;

import edu.curtin.oose.assignment2.probe.command.Command;

/**
 * Implementation of ProbeState interface.
 *
 * @author Joshua Orbon 20636948
 */
public class MovingState implements ProbeState
{
    /**
     * Handles how the List of move commands are stored.
     *
     * @param probe The Probe object that has delegated the task to the state
     * @param moves List of Command objects
     */
    @Override
    public void storeMoves(Probe probe, List<Command> moves)
    {
        // Set probe's commands
        probe.setCommands(moves);
    }

    /**
     * Handles how the List of measure commands are stored.
     *
     * @param probe       The Probe object that has delegated the task to the
     *                    state
     * @param measureList List of Command objects
     */
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
