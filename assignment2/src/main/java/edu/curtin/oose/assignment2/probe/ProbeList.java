package edu.curtin.oose.assignment2.probe;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.curtin.oose.assignment2.diagnostic.DiagnosticObserver;
import edu.curtin.oose.assignment2.diagnostic.DiagnosticWriter;
import edu.curtin.oose.assignment2.probe.command.Command;
import edu.curtin.oose.assignment2.probe.command.Measure;
import edu.curtin.oose.assignment2.probe.command.Move;

public class ProbeList
{
    private Map<String, Probe> probes;
    private List<DiagnosticObserver> writeObservers;
    private int currentSol;

    public ProbeList()
    {
        this.probes = new HashMap<>();
        this.writeObservers = new LinkedList<>();
        this.currentSol = 0;
    }

    public void addProbe(String name, Probe probe)
    {
        // Add new probe
        this.probes.put(name, probe);
    }

    public void addDiagnosticObserver(DiagnosticObserver observer)
    {
        this.writeObservers.add(observer);
    }

    public void removeDiagnosticObserver(DiagnosticObserver observer)
    {
        this.writeObservers.remove(observer);
    }

    public boolean hasProbe(String probeName)
    {
        return this.probes.containsKey(probeName);
    }

    public void instructMove(String probeName, double newLat, double newLongi)
    {
        Probe probe = probes.get(probeName);

        if(probe == null)
        {
            // TODO: Throw exception
            // throw new Exception("Probe not found");
            System.out.println("Probe not found");
        }

        double lat = probe.getLattitude();
        double longi = probe.getLongitude();

        // Calculate move commands
        List<Command> moves = generateMoveCommands(lat, longi, newLat, newLongi,
            probe.getMaxDistance());

        // Send move commands to probe
        probe.storeMoves(moves);
    }

    public void instructMeasure(
        String probeName, List<String> quantities, int num
    )
    {
        Probe probe = probes.get(probeName);
        if(probe == null)
        {
            // TODO: Throw exception
            // throw new Exception("Probe not found");
            System.out.println("Probe not found");
        }

        // Generate measure commands
        List<Command> measureList = generateMeasureCommands(quantities, num);

        // Store measure instructions
        probe.storeMeasure(measureList);
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
        System.out.printf("TO EARTH: %s", p.getStatus());
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
        this.currentSol++;

        for(Probe p : probes.values())
        {
            p.sendCommand(this.currentSol);
        }
    }

    public void writeDiagnostics(DiagnosticWriter w)
    {
        w.append("SOL " + this.currentSol + ":" + "\n");

        // Write probes to diagnostic
        notifyWrite(w);
    }

    private List<Command> generateMoveCommands(
        double currentLat, double currentLong, double targetLat,
        double targetLong, double maxDistance
    )
    {
        // calculate difference for lattitude and longitude
        double distanceLat = targetLat - currentLat;
        double distanceLong = targetLong - currentLong;

        // calculate distance between those two points
        double distance = Math.sqrt((distanceLat * distanceLat) + (distanceLong
            * distanceLong));

        // number of max distance movements
        int maxNum = (int)(distance / maxDistance);

        // find angle (adj = lattitude axis)
        double angle = Math.acos(distanceLat / distance);

        double maxDistanceLat = Math.cos(angle) * maxDistance;
        double maxDistanceLong = Math.sin(angle) * maxDistance;

        // remainder of movement
        double finalLat = Math.cos(angle) * (distance % maxDistance);
        double finalLong = Math.sin(angle) * (distance % maxDistance);

        List<Command> out = new LinkedList<>();
        for(int i = 0; i < maxNum; i++)
        {
            out.add(new Move(maxDistanceLat, maxDistanceLong));
        }

        // Remainder move instruction
        out.add(new Move(finalLat, finalLong));

        return out;
    }

    private List<Command> generateMeasureCommands(
        List<String> quantities, int num
    )
    {
        List<Command> out = new LinkedList<>();
        for(int i = 0; i < num; i++)
        {
            out.add(new Measure(quantities));
        }

        return out;
    }

    private void notifyWrite(DiagnosticWriter w)
    {
        for(DiagnosticObserver o : this.writeObservers)
        {
            o.write(w);
        }
    }
}
