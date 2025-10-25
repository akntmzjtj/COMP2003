package edu.curtin.oose.assignment2.probe.command;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import edu.curtin.oose.assignment2.probe.Probe;

/**
 * Implementation of Command interface. Simulates the movement command being
 * sent to a given probe and execution of the movement.
 *
 * @author Joshua Orbon 20636948
 */
public class Move implements Command
{
    private double lat;
    private double longi;

    /**
     * Constructor for Move instances.
     *
     * @param lat   Lattitude
     * @param longi Longitude
     */
    public Move(double lat, double longi)
    {
        this.lat = lat;
        this.longi = longi;
    }

    /**
     * Prints the movement instructed by the command
     *
     * @param probeName Name of the probe
     * @throws IllegalArgumentException When String is null or blank
     */
    @Override
    public void printCommand(String probeName)
    {
        if(probeName == null)
        {
            throw new IllegalArgumentException("Parameter is null.");
        }
        if(probeName.isBlank())
        {
            throw new IllegalArgumentException("String is blank.");
        }

        System.out.printf("TO %s: MOVE BY %+.6f %+.6f\n", probeName
            .toUpperCase(), this.lat, this.longi);
    }

    /**
     * Simulates the movement being processed by the probe
     *
     * @param probe The Probe object that has 'received' the command
     * @throws IllegalArgumentException When Probe is null
     */
    @Override
    public void execute(Probe probe)
    {
        if(probe == null)
        {
            throw new IllegalArgumentException("Parameter is null.");
        }

        // Move probe (simulation)
        double newLat = probe.getLattitude() + this.lat;
        double newLongi = probe.getLongitude() + this.longi;

        probe.setLattitude(newLat);
        probe.setLongitude(newLongi);
    }

    /**
     * A function for Probe object to use to format the command into a String
     * for storing in history.
     *
     * @param sol
     * @return formatted String detailing the command's execution
     * @throws IllegalArgumentException When int is negative
     */
    @Override
    public String save(int sol)
    {
        // If martian day is negative
        if(sol < 0)
        {
            throw new IllegalArgumentException(
                "Martian day cannot be negative.");
        }

        return String.format("SOL %d: %+.6f %+.6f", sol, this.lat, this.longi);
    }

    /**
     * A getter function for the lattitude and longitude, formatted
     *
     * @return List of data of type String
     */
    @Override
    public List<String> getData()
    {
        List<String> list = new LinkedList<>();
        list.add(String.format("%.6f", this.lat));
        list.add(String.format("%.6f", this.longi));

        return Collections.unmodifiableList(list);
    }
}
