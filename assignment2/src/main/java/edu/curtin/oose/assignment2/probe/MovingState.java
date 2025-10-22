package edu.curtin.oose.assignment2.probe;

import java.util.List;

import edu.curtin.oose.assignment2.probe.command.Command;

public class MovingState implements ProbeState
{
    @Override
    public String getState()
    {
        return "MOVING";
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
        // Stop moving and store commands
        probe.setCommands(measureList);

        // Update state
        probe.setState(Probe.MEASURING_STATE);
    }

    @Override
    public void simulateMove(Probe probe, double lat, double longi)
    {
        probe.setLattitude(probe.getLattitude() + lat);
        probe.setLongitude(probe.getLongitude() + longi);
    }

    @Override
    public void executeMeasure(Probe probe, List<String> toMeasure)
    {
        probe.setState(Probe.MEASURING_STATE);
        probe.saveMeasure(toMeasure);
    }
}
