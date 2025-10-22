package edu.curtin.oose.assignment2.probe;

import java.util.List;

public class MovingState implements ProbeState
{
    @Override
    public String getState()
    {
        return "DRIVING";
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
