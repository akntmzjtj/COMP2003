package edu.curtin.oose.assignment2.probe;

import java.util.List;

import edu.curtin.oose.assignment2.probe.command.Command;

public class LowPowerState implements ProbeState
{
    @Override
    public String getState()
    {
        return "LOW POWER MODE";
    }

    @Override
    public void sendMove(Probe probe, List<Command> moves)
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
    public void simulateMove(Probe probe, double lat, double longi)
    {
        probe.setLattitude(probe.getLattitude() + lat);
        probe.setLongitude(probe.getLongitude() + longi);
        probe.setState(Probe.MOVING_STATE);
    }

    @Override
    public void executeMeasure(Probe probe, List<String> toMeasure)
    {
        probe.setState(Probe.MEASURING_STATE);
        probe.saveMeasure(toMeasure);
    }
}
