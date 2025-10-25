package edu.curtin.oose.assignment2.probe.command;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import edu.curtin.oose.assignment2.probe.Probe;

/**
 * Implementation of Command interface. Simulates a probe measuring specified
 * quantities.
 *
 * @author Joshua Orbon 20636948
 */
public class Measure implements Command
{
    // Random object used to simulate measurements
    private static final Random RANDOM = new Random();

    private List<String> data;
    private String measurements;

    /**
     * Constructor for Measure instances.
     *
     * @param quantities List of quantities for a probe to measure at their
     *                   current location (simulation)
     * @throws IllegalArgumentException When List is null, empty or contains
     *                                  null
     */
    public Measure(List<String> quantities)
    {
        // List of quantities cannot be null, empty or contain null
        if(quantities == null)
        {
            throw new IllegalArgumentException("Parameter is null.");
        }
        if(quantities.isEmpty())
        {
            throw new IllegalArgumentException("List is empty.");
        }
        if(quantities.contains(null))
        {
            throw new IllegalArgumentException("List contains null.");
        }

        // Data to be measured
        this.data = new LinkedList<>(quantities);
        this.measurements = null;
    }

    /**
     * Prints the quantities a probe is currently measuring.
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

        // Create temp
        List<String> temp = new LinkedList<>(data);

        String quantitiesPrint = temp.removeFirst().toUpperCase();
        for(String s : temp)
        {
            quantitiesPrint += ", " + s.toUpperCase();
        }

        System.out.printf("TO %s: MEASURE %s\n", probeName.toUpperCase(),
            quantitiesPrint);
    }

    /**
     * Simulates the measurement of quantities instructed for the probe.
     *
     * @param probe The Probe object that has 'received' the command
     * @throws IllegalArgumentException When Probe is null
     * @throws IllegalStateException    When this.measurements String is null or
     *                                  blank
     */
    @Override
    public void execute(Probe probe)
    {
        if(probe == null)
        {
            throw new IllegalArgumentException("Parameter is null.");
        }

        // Generate random numbers for each quantity
        formatMeasurements();

        // If for whatever reason...
        if(this.measurements == null)
        {
            throw new IllegalStateException(
                "Measurements were not done prior.");
        }
        if(this.measurements.isBlank())
        {
            throw new IllegalStateException("No quantities were measured.");
        }

        // Simulate probe measuring quantities and print
        System.out.printf("TO EARTH: FROM %s: %s\n", probe.getName()
            .toUpperCase(), this.measurements);
    }

    /**
     * A function for Probe object to use to format the command into a String
     * for storing in history.
     *
     * @param sol
     * @return formatted String detailing the command's execution
     * @throws IllegalStateException    When this.measurements is null or blank
     * @throws IllegalArgumentException When int is negative
     */
    @Override
    public String save(int sol)
    {
        // If formatMeasurements() was not called prior
        if(this.measurements == null)
        {
            throw new IllegalStateException(
                "Command cannot be saved without being executed.");
        }
        if(this.measurements.isBlank())
        {
            throw new IllegalStateException(
                "Command cannot be saved when no quantities were measured.");
        }

        // If martian day is negative
        if(sol < 0)
        {
            throw new IllegalArgumentException(
                "Martian day cannot be negative.");
        }

        return String.format("SOL %d: %s", sol, this.measurements);
    }

    /**
     * A getter function for the quantities being measured.
     *
     * @return List of data of type String
     */
    @Override
    public List<String> getData()
    {
        return Collections.unmodifiableList(this.data);
    }

    /**
     * Generates random floating point numbers per quantity and is stored into the object.
     */
    private void formatMeasurements()
    {
        // Store data into temp list
        List<String> temp = new LinkedList<>(this.data);

        String randDouble = String.format("%.4f", RANDOM.nextDouble(1));
        this.measurements = temp.removeFirst().toUpperCase() + "=" + randDouble;

        // Repeat above
        for(String s : temp)
        {
            randDouble = String.format("%.4f", RANDOM.nextDouble(1));
            this.measurements += " " + s.toUpperCase() + "=" + randDouble;
        }
    }
}
