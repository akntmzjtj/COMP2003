package edu.curtin.oose.assignment2.probe;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import edu.curtin.oose.assignment2.probe.command.Command;

/**
 * The Probe class is a representation of the physical probe deployed on Mars.
 * It keeps track of its location and can display what it's currently measuring.
 * It's mainly a simulation and cannot instruct a physical probe to move or to
 * return actual measurements.
 */
public class Probe implements NextDayObservers
{
    // States
    protected static final ProbeState LOW_POWER_STATE = new LowPowerState();
    protected static final ProbeState MOVING_STATE = new MovingState();
    protected static final ProbeState MEASURING_STATE = new MeasuringState();

    private Random random;

    private String name;
    private double currentLat;
    private double currentLong;
    private ProbeState state;

    private int currentSol;
    private List<Command> commands;
    private LinkedList<String> commandHistory;
    private List<String> lastQuantitiesMeasured;

    public Probe(String name, double lat, double longi)
    {
        this.name = name;
        this.state = LOW_POWER_STATE;
        this.currentSol = 0;
        this.commands = new LinkedList<>();
        this.commandHistory = new LinkedList<>();
        this.lastQuantitiesMeasured = new LinkedList<>();

        // set current coordinates
        this.currentLat = lat;
        this.currentLong = longi;

        this.random = new Random();
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
        List<String> list = this.commandHistory;
        return Collections.unmodifiableList(list);
    }

    public List<String> getLastQuantitiesMeasured()
    {
        return Collections.unmodifiableList(this.lastQuantitiesMeasured);
    }

    protected void setLattitude(double lat)
    {
        this.currentLat = lat;
    }

    protected void setLongitude(double longi)
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

    public void standBy()
    {
        this.state = Probe.LOW_POWER_STATE;
    }

    private void simulateMove(String command)
    {
        // Split command into 6 elements, as last two elements contains
        // target coordinates
        String[] commandSplit = command.split(" ", 6);

        if(commandSplit.length != 6)
        {
            throw new IllegalArgumentException("Move command is invalid.");
        }

        Double lat = Double.parseDouble(commandSplit[4]);
        Double longi = Double.parseDouble(commandSplit[5]);

        // Let state handle the move
        state.simulateMove(this, lat, longi);

        // Save the command
        String save = String.format("SOL %d: %+.6f %+.6f", this.currentSol, lat,
            longi);
        this.commandHistory.add(save);
    }

    public void simulateMeasure(String command)
    {
        // Remove commas
        command = command.replaceAll(",", "");

        // Split command
        String[] commandSplit = command.split(" ");

        // Clear last measured
        this.lastQuantitiesMeasured.clear();

        // Iterate from fourth element according to command format
        List<String> toMeasure = new LinkedList<>();
        for(int i = 3; i < commandSplit.length; i++)
        {
            this.lastQuantitiesMeasured.add(commandSplit[i]);
            toMeasure.add(commandSplit[i]);
        }

        // Let state handle the move
        this.state.executeMeasure(this, toMeasure);
    }

    protected void saveMeasure(List<String> toMeasure)
    {
        String rand = String.format("%.4f", this.random.nextDouble(1));
        String measured = toMeasure.removeFirst() + "=" + rand;

        for(String s : toMeasure)
        {
            rand = String.format("%.4f", this.random.nextDouble(1));
            measured += " " + s + "=" + rand;
        }

        // Save command
        String save = String.format("SOL %d: %s", this.currentSol, measured);
        this.commandHistory.add(save);

        // PRINT
        System.out.printf("TO EARTH: %s: %s\n", this.name.toUpperCase(),
            measured);
    }

    public void runCommand(String command)
    {
        if(command.contains("MOVE BY"))
        {
            // Simulate move
            simulateMove(command);
        }
        else if(command.contains("MEASURE"))
        {
            // Simulate measure
            simulateMeasure(command);
        }
    }

    public void incrementSol()
    {
        this.currentSol++;
    }

    public void sendMove(List<Command> moves)
    {
        // Let current state handle new list of commands
        this.state.sendMove(this, moves);
    }

    public void sendCommand()
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
            c.printCommand(this.name);

            // Store command
            this.commandHistory.add(c.save(this.currentSol));
        }
    }
}
