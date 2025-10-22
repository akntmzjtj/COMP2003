package edu.curtin.oose.assignment2.probe;

import java.util.LinkedList;
import java.util.List;

import edu.curtin.oose.assignment2.probe.command.Command;
import edu.curtin.oose.assignment2.probe.command.Measure;

public class MeasuringState implements ProbeState
{
    @Override
    public String getState()
    {
        return "MEASURING";
    }

    @Override
    public void storeMoves(Probe probe, List<Command> moves)
    {
        // Set probe's commands
        probe.setCommands(moves);
    }

    @Override
    public void storeMeasure(Probe probe, List<Command> measureList)
    {
        // Generate new list of commands using current and new quantities
        List<Command> newCommands = new LinkedList<>();

        // Add current quantities being measured
        List<String> totalQuantities = new LinkedList<>(probe.getCommands()
            .getFirst().getData());

        System.out.println("DEBUG: currentSize: " + totalQuantities.size());

        // Add new quantities to be measured
        for(String s : measureList.getFirst().getData())
        {
            // If quantity is not in list, add
            if(!totalQuantities.contains(s))
            {
                totalQuantities.add(s);
            }
        }

        System.out.println("DEBUG: totalSize: " + totalQuantities.size());

        for(int i = 0; i < measureList.size(); i++)
        {
            newCommands.add(new Measure(totalQuantities));
        }

        // Set as new commands
        probe.setCommands(newCommands);
    }

    @Override
    public void simulateMove(Probe probe, double lat, double longi)
    {
        probe.setLattitude(probe.getLattitude() + lat);
        probe.setLongitude(probe.getLongitude() + longi);
        probe.setState(Probe.MOVING_STATE);
    }

    @Override
    public void executeMeasure(Probe probe, List<String> toMeasure)
    {
        probe.saveMeasure(toMeasure);
    }
}
