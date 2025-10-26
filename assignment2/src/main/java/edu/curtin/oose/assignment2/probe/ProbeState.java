package edu.curtin.oose.assignment2.probe;

import java.util.List;

import edu.curtin.oose.assignment2.probe.command.Command;

/**
 * Interface for states of Probe objects. Delegates the function to ProbeState
 * implementations, allowing state-dependent code.
 *
 * @author Joshua Orbon 20636948
 */
public interface ProbeState
{
    /**
     * Handles how the List of move commands are stored.
     *
     * @param probe The Probe object that has delegated the task to the state
     * @param moves List of Command objects
     */
    void storeMoves(Probe probe, List<Command> moves);

    /**
     * Handles how the List of measure commands are stored.
     *
     * @param probe The Probe object that has delegated the task to the state
     * @param moves List of Command objects
     */
    void storeMeasure(Probe probe, List<Command> measureList);
}
