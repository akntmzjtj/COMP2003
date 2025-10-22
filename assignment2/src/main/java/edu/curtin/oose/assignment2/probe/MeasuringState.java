package edu.curtin.oose.assignment2.probe;

import java.util.List;

import edu.curtin.oose.assignment2.probe.command.Command;

public class MeasuringState implements ProbeState
{
    @Override
    public String getState()
    {
        return "MEASURING";
    }

    @Override
    public void sendMove(Probe probe, List<Command> moves)
    {
        // Set probe's commands
        probe.setCommands(moves);
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
