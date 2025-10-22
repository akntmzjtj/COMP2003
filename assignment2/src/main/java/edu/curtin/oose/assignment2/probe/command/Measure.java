package edu.curtin.oose.assignment2.probe.command;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Measure implements Command
{
    private List<String> data;
    private Random random; // use random values to simulate measure

    public Measure(Random random, List<String> quantities)
    {
        this.data = new LinkedList<>(quantities);
        this.random = random;
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

        System.out.printf("TO %s: MEASURE %s", probeName.toUpperCase(),
                quantitiesPrint);
    }

    @Override
    public String save(int sol)
    {
        return null;
    }

    @Override
    public String execute()
    {
        // Store data into temp list
        List<String> temp = new LinkedList<>(this.data);

        String randDouble = String.format("%.4f", this.random.nextDouble(1));
        String out = temp.removeFirst() + "=" + randDouble;

        // Repeat above
        for(String s : temp)
        {
            randDouble = String.format("%.4f", this.random.nextDouble(1));
            out += " " + s + "=" + randDouble;
        }

        return out;
    }

    @Override
    public List<String> getData()
    {
        return Collections.unmodifiableList(this.data);
    }
}
