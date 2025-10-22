package edu.curtin.oose.assignment2.probe;

import java.util.List;

public class MeasuringState implements ProbeState
{
    @Override
    public String getState()
    {
        return "MEASURING";
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
