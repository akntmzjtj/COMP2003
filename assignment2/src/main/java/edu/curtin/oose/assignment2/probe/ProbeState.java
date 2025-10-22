package edu.curtin.oose.assignment2.probe;

import java.util.List;

public interface ProbeState
{
    String getState();
    void simulateMove(Probe probe, double lat, double longi);
    void executeMeasure(Probe probe, List<String> toMeasure);
}
