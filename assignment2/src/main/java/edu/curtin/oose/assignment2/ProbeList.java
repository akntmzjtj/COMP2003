package edu.curtin.oose.assignment2;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.curtin.oose.assignment2.probe.NextDayObservers;
import edu.curtin.oose.assignment2.probe.Probe;
import edu.curtin.oose.assignment2.probe.command.Command;
import edu.curtin.oose.assignment2.probe.command.Move;

public class ProbeList
{
    private Map<String, Probe> probes;
    private List<NextDayObservers> nextDayObserveres;

    public ProbeList()
    {
        this.probes = new HashMap<>();
        this.nextDayObserveres = new LinkedList<>();
    }

    public void addProbe(String name, Probe probe)
    {
        // Add new probe
        this.probes.put(name, probe);

        // Add as observer
        this.nextDayObserveres.add(probe);
    }

    public void instructMove(String probeName, double newLat, double newLongi)
    {
        // Check if probeName is rover or drone
        double maxDegree = 0;
        if(probeName.contains("rover"))
        {
            maxDegree = 0.004;
        }
        else if(probeName.contains("drone"))
        {
            maxDegree = 0.018;
        }

        Probe probe = probes.get(probeName);
        double lat = probe.getLattitude();
        double longi = probe.getLongitude();

        // Calculate move commands
        List<Command> moves = generateMoveCommands(lat, longi, newLat, newLongi, maxDegree);

        // Send move commands to probe
        probe.storeMoves(moves);
    }

    // public void instructMeasure(
    //     String probeName, List<String> quantities, int num
    // )
    // {
    //     // Get probe
    //     Probe p = this.probes.get(probeName);

    //     if(p == null)
    //     {
    //         // TODO: Throw exception
    //         // throw new Exception("Probe not found");
    //         System.out.println("Probe not found");
    //     }

    //     // Check status of the probe
    //     String state = p.getState();

    //     // Clear commands
    //     LinkedList<String> commands = probesCommands.get(probeName);
    //     commands.clear();

    //     if(state.equals("MEASURING"))
    //     {
    //         // Get the quantities currently measuring and add to new list
    //         for(String s : p.getLastQuantitiesMeasured())
    //         {
    //             if(!quantities.contains(s.toLowerCase()))
    //             {
    //                 quantities.add(s.toLowerCase());
    //             }
    //         }
    //     }

    //     // Generate measure commands
    //     String quantitiesPrint = quantities.removeFirst().toUpperCase();
    //     for(String s : quantities)
    //     {
    //         quantitiesPrint += ", " + s.toUpperCase();
    //     }

    //     String c;
    //     for(int i = 0; i < num; i++)
    //     {
    //         c = String.format("TO %s: MEASURE %s", probeName.toUpperCase(),
    //             quantitiesPrint);
    //         commands.add(c);
    //     }
    // }

    public void getProbeStatus(String probeName)
    {
        Probe p = probes.get(probeName);

        if(p == null)
        {
            // TODO: Throw exception
            // throw new Exception("Probe not found");
            System.out.println("Probe not found");
        }

        // Print status
        System.out.printf("TO EARTH: %s at %.6f %.6f, %s\n", p.getName()
            .toUpperCase(), p.getLattitude(), p.getLongitude(), p.getState());
    }

    public void getProbeHistory(String probeName)
    {
        Probe p = probes.get(probeName);

        if(p == null)
        {
            // TODO: Throw exception
            // throw new Exception("Probe not found");
            System.out.println("Probe not found");
        }

        // Print history
        List<String> probeHistory = p.getHistory();

        System.out.println("TO EARTH: " + probeName.toUpperCase()
            + " ACTIVITIES:");
        for(String s : probeHistory)
        {
            System.out.println("    " + s);
        }
    }

    /**
     * Send commands to physical probe (simply prints it for simulation)
     */
    public void sendCommands()
    {
        // Next day for probes
        nextDay();

        for(Probe p : probes.values())
        {
            p.sendCommand();
        }
    }

    private List<Command> generateMoveCommands(
        double currentLat, double currentLong, double targetLat, double targetLong, double maxDegree
    )
    {
        // calculate difference for lattitude and longitude
        double distanceLat = targetLat - currentLat;
        double distanceLong = targetLong - currentLong;

        // calculate distance between those two points
        double distance = Math.sqrt((distanceLat * distanceLat) + (distanceLong
            * distanceLong));

        // number of max degree movements
        int maxNum = (int)(distance / maxDegree);

        // find angle (adj = lattitude axis)
        double angle = Math.acos(distanceLat / distance);

        double maxDegreeLat = Math.cos(angle) * maxDegree;
        double maxDegreeLong = Math.sin(angle) * maxDegree;

        // remainder degree movement
        double finalDegreeLat = Math.cos(angle) * (distance % maxDegree);
        double finalDegreeLong = Math.sin(angle) * (distance % maxDegree);

        List<Command> out = new LinkedList<>();
        for(int i = 0; i < maxNum; i++)
        {
            out.add(new Move(maxDegreeLat, maxDegreeLong));
        }

        out.add(new Move(finalDegreeLat, finalDegreeLong));

        return out;
    }

    // private List<Command> generateMeasureCommands()
    // {
    // }

    private void nextDay()
    {
        for(NextDayObservers m : nextDayObserveres)
        {
            m.incrementSol();
        }
    }
}
