package edu.curtin.oose.assignment2.probe.command;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import edu.curtin.oose.assignment2.probe.Probe;

public class Measure implements Command
{
    private List<String> data;
    private Random random; // use random values to simulate measure
    private String measurements;

    public Measure(List<String> quantities)
    {
        this.data = new LinkedList<>(quantities);
        this.random = new Random();
        this.measurements = null;
    }

    @Override
    public void printCommand(String probeName)
    {
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

    @Override
    public String save(int sol)
    {
        if(this.measurements == null)
        {
            throw new IllegalStateException("Command cannot be saved without being executed");
        }

        return this.measurements;
    }

    @Override
    public void execute(Probe probe)
    {
        formatMeasurements();

        // Simulate probe measuring quantities and print
        System.out.printf("TO EARTH: FROM %s: %s\n", probe.getName()
            .toUpperCase(), measurements);
    }

    @Override
    public List<String> getData()
    {
        return Collections.unmodifiableList(this.data);
    }

    private void formatMeasurements()
    {
        // Store data into temp list
        List<String> temp = new LinkedList<>(this.data);

        String randDouble = String.format("%.4f", this.random.nextDouble(1));
        this.measurements = temp.removeFirst().toUpperCase() + "=" + randDouble;

        // Repeat above
        for(String s : temp)
        {
            randDouble = String.format("%.4f", this.random.nextDouble(1));
            this.measurements += " " + s.toUpperCase() + "=" + randDouble;
        }
    }
}
