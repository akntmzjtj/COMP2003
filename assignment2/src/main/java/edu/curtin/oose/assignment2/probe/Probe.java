package edu.curtin.oose.assignment2.probe;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import edu.curtin.oose.assignment2.probe.command.Command;

/**
 * The Probe class is a representation of the physical probe deployed on Mars.
 * It keeps track of its location and can display what it's currently measuring.
 * It's mainly a simulation and cannot instruct a physical probe to move or to
 * return actual measurements.
 */
public class Probe implements DiagnosticObserver
{
    // States
    protected static final ProbeState LOW_POWER_STATE = new LowPowerState();
    protected static final ProbeState MOVING_STATE = new MovingState();
    protected static final ProbeState MEASURING_STATE = new MeasuringState();

    private String name;
    private double currentLat;
    private double currentLong;
    private ProbeState state;

    private List<Command> commands;
    private List<String> commandHistory;

    public Probe(String name, double lat, double longi)
    {
        this.name = name;
        this.state = LOW_POWER_STATE;
        this.commands = new LinkedList<>();
        this.commandHistory = new LinkedList<>();

        // set current coordinates
        this.currentLat = lat;
        this.currentLong = longi;
    }

    public String getName()
    {
        return this.name;
    }

    public double getLattitude()
    {
        return currentLat;
    }

    public double getLongitude()
    {
        return currentLong;
    }

    public String getState()
    {
        return this.state.getState();
    }

    public List<Command> getCommands()
    {
        return Collections.unmodifiableList(this.commands);
    }

    public List<String> getHistory()
    {
        return Collections.unmodifiableList(this.commandHistory);
    }

    public void setLattitude(double lat)
    {
        this.currentLat = lat;
    }

    public void setLongitude(double longi)
    {
        this.currentLong = longi;
    }

    protected void setState(ProbeState state)
    {
        this.state = state;
    }

    public void setCommands(List<Command> commands)
    {
        this.commands = commands;
    }

    public void storeMoves(List<Command> moves)
    {
        // Let current state handle new list of commands
        this.state.storeMoves(this, moves);
    }

    public void storeMeasure(List<Command> measureList)
    {
        // Let current state handle new list of commands
        this.state.storeMeasure(this, measureList);
    }

    public void sendCommand(int sol)
    {
        // If empty, set to low state mode
        if(this.commands.isEmpty())
        {
            this.state = Probe.LOW_POWER_STATE;
        }
        else
        {
            // Remove first command from list and print
            Command c = this.commands.removeFirst();

            // Print command (simulate send)
            c.printCommand(this.name);

            // Probe execute command (simulation)
            c.execute(this);

            // Store command
            this.commandHistory.add(c.save(sol));
        }
    }

    @Override
    public void write(DiagnosticWriter w)
    {
        // Append status
        String s = String.format("    %s at %.6f %.6f, %s\n", this.name
            .toUpperCase(), this.currentLat, this.currentLong, this.getState());
        w.append(s);
    }
}
