package edu.curtin.oose.assignment2.probe.command;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Move implements Command
{
    private double lat;
    private double longi;

    public Move(double lat, double longi)
    {
        this.lat = lat;
        this.longi = longi;
    }

    @Override
    public void printCommand(String probeName)
    {
        System.out.printf("TO %s: MOVE BY %+.6f %+.6f\n", probeName
            .toUpperCase(), this.lat, this.longi);
    }

    @Override
    public String save(int sol)
    {
        return  String.format("SOL %d: %+.6f %+.6f", sol, this.lat,
            this.longi);
    }

    @Override
    public String execute()
    {
        return String.format("%.6f %.6f", this.lat, this.longi);
    }

    @Override
    public List<String> getData()
    {
        List<String> list = new LinkedList<>();
        list.add(String.format("%.6f", this.lat));
        list.add(String.format("%.6f", this.longi));

        return Collections.unmodifiableList(list);
    }
}
