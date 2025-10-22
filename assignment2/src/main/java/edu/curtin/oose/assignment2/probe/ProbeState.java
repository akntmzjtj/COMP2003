package edu.curtin.oose.assignment2.probe;

import java.util.List;

import edu.curtin.oose.assignment2.probe.command.Command;

public interface ProbeState
{
    String getState();
    void sendMove(Probe probe, List<Command> moves);
    void simulateMove(Probe probe, double lat, double longi);
    void executeMeasure(Probe probe, List<String> toMeasure);
}
