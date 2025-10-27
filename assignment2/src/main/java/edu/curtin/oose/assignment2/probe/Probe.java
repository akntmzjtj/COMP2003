package edu.curtin.oose.assignment2.probe;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import edu.curtin.oose.assignment2.diagnostic.DiagnosticObserver;
import edu.curtin.oose.assignment2.diagnostic.DiagnosticWriter;
import edu.curtin.oose.assignment2.diagnostic.DiagnosticWriterException;
import edu.curtin.oose.assignment2.probe.command.Command;

/**
 * The Probe class is a representation of the physical probe deployed on Mars.
 * It keeps track of its location and can display what it's currently measuring.
 * It's mainly a simulation and cannot instruct a physical probe to move or to
 * return actual measurements.
 *
 * @author Joshua Orbon 20636948
 */
public class Probe implements DiagnosticObserver
{
    // States
    protected static final ProbeState LOW_POWER_STATE = new LowPowerState();
    protected static final ProbeState MOVING_STATE = new MovingState();
    protected static final ProbeState MEASURING_STATE = new MeasuringState();

    private String name;
    private double lat;
    private double longi;
    private final double maxDistance; // max travel distance per move
                                      // instruction
    private ProbeState state;

    private List<Command> commands;
    private List<String> commandHistory;

    /**
     * Constructor for Probe.
     *
     * @param name        Name of the probe
     * @param lat         Initial latitude of probe
     * @param longi       Initial longitude of probe
     * @param maxDistance Max distance a probe can travel per sol
     * @throws IllegalArgumentException When name provided is null or blank,
     *                                  maxDistance is less than or equal to
     *                                  zero
     */
    public Probe(String name, double lat, double longi, double maxDistance)
    {
        if(name == null)
        {
            throw new IllegalArgumentException("Name provided is null.");
        }
        if(name.isBlank())
        {
            throw new IllegalArgumentException("Name provided is blank.");
        }

        if(maxDistance <= 0)
        {
            throw new IllegalArgumentException(
                "Max travel distance provided is less than or equal to zero.");
        }

        this.name = name;
        this.lat = lat;
        this.longi = longi;
        this.maxDistance = maxDistance;
        this.state = LOW_POWER_STATE;

        // Initialise list of current commands and command history
        this.commands = new LinkedList<>();
        this.commandHistory = new LinkedList<>();
    }

    /**
     * Getter for name of the probe.
     *
     * @return name of probe
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Getter for probe's current latitude.
     *
     * @return lat
     */
    public double getLatitude()
    {
        return lat;
    }

    /**
     * Getter for probe's current longitude.
     *
     * @return longi
     */
    public double getLongitude()
    {
        return longi;
    }

    /**
     * Getter for the max travel distance the probe can travel per sol.
     *
     * @return
     */
    public double getMaxDistance()
    {
        return this.maxDistance;
    }

    /**
     * Getter for a probe's current status (simulation).
     *
     * @return Status of probe
     */
    public String getStatus()
    {
        return this.status();
    }

    /**
     * Getter for Probe object's current list of commands.
     *
     * @return List of Command objects
     */
    public List<Command> getCommands()
    {
        return Collections.unmodifiableList(this.commands);
    }

    /**
     * Getter for Probe object's command history.
     *
     * @return List of Strings detailing probe's status per sol
     */
    public List<String> getHistory()
    {
        return Collections.unmodifiableList(this.commandHistory);
    }

    /**
     * Setter for probe's latitude.
     *
     * @param lat New latitude
     */
    public void setLatitude(double lat)
    {
        this.lat = lat;
    }

    /**
     * Setter for probe's longitude.
     *
     * @param longi New longitude
     */
    public void setLongitude(double longi)
    {
        this.longi = longi;
    }

    /**
     * Setter for probe's state.
     *
     * @param state ProbeState object
     * @throws IllegalArgumentException When ProbeState is null
     */
    protected void setState(ProbeState state)
    {
        if(state == null)
        {
            throw new IllegalArgumentException("Parameter is null.");
        }

        this.state = state;
    }

    /**
     * Setter for probe's list of commands
     *
     * @param commands List of Command objects
     */
    public void setCommands(List<Command> commands)
    {
        this.commands = commands;
    }

    /**
     * Stores move commands depending on state.
     *
     * @param moves List of Command objects
     * @throws IllegalArgumentException When List is null or contains null
     */
    public void storeMoves(List<Command> moves)
    {
        if(moves == null)
        {
            throw new IllegalArgumentException("Parameter is null.");
        }
        if(moves.contains(null))
        {
            throw new IllegalArgumentException("List contains null.");
        }

        // Let current state handle new list of commands
        this.state.storeMoves(this, moves);
    }

    /**
     * Stores measure commands depending on state
     *
     * @param measureList List of Command objects
     * @throws IllegalArgumentException When List is null or contains null
     */
    public void storeMeasure(List<Command> measureList)
    {
        if(measureList == null)
        {
            throw new IllegalArgumentException("Parameter is null.");
        }
        if(measureList.contains(null))
        {
            throw new IllegalArgumentException("List contains null.");
        }

        // Let current state handle new list of commands
        this.state.storeMeasure(this, measureList);
    }

    /**
     * Simulates the command being sent from the satellite to the probe.
     *
     * @param sol Current sol (Martian day)
     */
    public void sendCommand(int sol)
    {
        // If empty, set to low state mode
        if(this.commands.isEmpty())
        {
            this.state = LOW_POWER_STATE;
        }
        else
        {
            // Remove first command from list and print
            Command c = this.commands.removeFirst();

            // For whatever reason...
            if(c == null)
            {
                throw new IllegalStateException(
                    "Probe has stored null command.");
            }

            // Print command (simulate send)
            c.printCommand(this.name);

            // Probe execute command (simulation)
            c.execute(this);

            // Store command
            this.commandHistory.add(c.save(sol));
        }
    }

    /**
     * Observer method, writes current status of Probe
     *
     * @param w DiagnosticWriter object, handles writing of String
     */
    @Override
    public void write(DiagnosticWriter w) throws DiagnosticWriterException
    {
        // Append status
        w.append("   " + this.status());
    }

    /**
     * Formats name, current coordinates and state into a String.
     *
     * @return Status of probe
     */
    private String status()
    {
        return String.format("%s at %.6f %.6f, %s\n", this.name.toUpperCase(),
            this.lat, this.longi, this.state);
    }
}
