package edu.curtin.oose.assignment2;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.curtin.oose.assignment2.probe.NextDayObservers;
import edu.curtin.oose.assignment2.probe.Probe;

public class ProbeList
{
    private Map<String, Probe> probes;
    private List<NextDayObservers> nextDayObserveres;
    private Map<String, LinkedList<String>> probesCommands;

    public ProbeList()
    {
        this.probes = new HashMap<>();
        this.nextDayObserveres = new LinkedList<>();
        this.probesCommands = new HashMap<>();
    }

    public void addProbe(String name, Probe probe)
    {
        // Add new probe
        this.probes.put(name, probe);

        // Add as observer
        this.nextDayObserveres.add(probe);

        // Initialise probe commands and history string list
        this.probesCommands.put(name, new LinkedList<>());
    }

    public void moveProbe(String probeName, double newLat, double newLongi)
    {
        // Clear current set of commands for probe
        LinkedList<String> commands = probesCommands.get(probeName);
        commands.clear();

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

        // calculate move commands
        calculateMoveCommands(commands, probes.get(probeName), newLat, newLongi,
            maxDegree);
    }

    public void instructMeasure(
        String probeName, List<String> quantities, int num
    )
    {
        // Get probe
        Probe p = this.probes.get(probeName);

        if(p == null)
        {
            // TODO: Throw exception
            // throw new Exception("Probe not found");
            System.out.println("Probe not found");
        }

        // Check status of the probe
        String state = p.getState();

        // Clear commands
        LinkedList<String> commands = probesCommands.get(probeName);
        commands.clear();

        if(state.equals("MEASURING"))
        {
            // Get the quantities currently measuring and add to new list
            for(String s : p.getLastQuantitiesMeasured())
            {
                if(!quantities.contains(s.toLowerCase()))
                {
                    quantities.add(s.toLowerCase());
                }
            }
        }

        // Generate measure commands
        String quantitiesPrint = quantities.removeFirst().toUpperCase();
        for(String s : quantities)
        {
            quantitiesPrint += ", " + s.toUpperCase();
        }

        String c;
        for(int i = 0; i < num; i++)
        {
            c = String.format("TO %s: MEASURE %s", probeName.toUpperCase(), quantitiesPrint);
            commands.add(c);
        }
    }

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

    public void sendCommands()
    {
        // Next day for probes
        nextDay();

        for(Map.Entry<String, LinkedList<String>> entry : probesCommands
            .entrySet())
        {
            String probeName = entry.getKey();
            LinkedList<String> list = entry.getValue();

            Probe p = probes.get(probeName);

            if(list.isEmpty())
            {
                // Set probe to lower power mode
                p.standBy();
            }
            else
            {
                // Remove command from command list
                String command = list.removeFirst();

                // Print and send out command
                System.out.println(command);
                p.runCommand(command);
            }
        }
    }

    private void calculateMoveCommands(
        LinkedList<String> probeCommands, Probe probe, double targetLat,
        double targetLong, double maxDegree
    )
    {
        // current position
        double currentLat = probe.getLattitude();
        double currentLong = probe.getLongitude();

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

        for(int i = 0; i < maxNum; i++)
        {
            probeCommands.add(String.format("TO %s: MOVE BY %+.6f %+.6f", probe
                .getName().toUpperCase(), maxDegreeLat, maxDegreeLong));
        }

        probeCommands.add(String.format("TO %s: MOVE BY %+.6f %+.6f", probe
            .getName().toUpperCase(), finalDegreeLat, finalDegreeLong));
    }

    private void nextDay()
    {
        for(NextDayObservers m : nextDayObserveres)
        {
            m.incrementSol();
        }
    }
}
