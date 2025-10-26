package edu.curtin.oose.assignment2.probe;

import java.util.LinkedList;
import java.util.List;

import edu.curtin.oose.assignment2.probe.command.Command;
import edu.curtin.oose.assignment2.probe.command.Measure;

/**
 * Implementation of ProbeState interface.
 *
 * @author Joshua Orbon 20636948
 */
public class MeasuringState implements ProbeState
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

        // Update state
        probe.setState(Probe.MOVING_STATE);
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
        // Generate new list of commands using current and new quantities
        List<Command> newCommands = new LinkedList<>();

        // Store current commands
        List<Command> commands = probe.getCommands();

        if(commands == null)
        {
            throw new IllegalStateException("Commands were never sent.");
        }

        // If in measure state and another list of measure commands were 'sent'
        if(commands.isEmpty())
        {
            newCommands = measureList;
        }
        else
        {
            // Add current quantities being measured
            List<String> current = commands.getFirst().getData();
            List<String> totalQuantities = new LinkedList<>(current);

            // Add new quantities to be measured
            for(String s : measureList.getFirst().getData())
            {
                // If quantity is not in list, add
                if(!totalQuantities.contains(s))
                {
                    totalQuantities.add(s);
                }
            }

            // Create new list of measure command with new quantities
            int length = measureList.size();
            for(int i = 0; i < length; i++)
            {
                newCommands.add(new Measure(totalQuantities));
            }
        }

        // Set as new commands
        probe.setCommands(newCommands);
    }

    @Override
    public String toString()
    {
        return "MEASURING";
    }
}
