package edu.curtin.oose.assignment2;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.curtin.oose.assignment2.probe.DiagnosticWriter;
import edu.curtin.oose.assignment2.probe.NextDayObserver;
import edu.curtin.oose.assignment2.probe.Probe;
import edu.curtin.oose.assignment2.probe.DiagnosticObserver;
import edu.curtin.oose.assignment2.probe.command.Command;
import edu.curtin.oose.assignment2.probe.command.Measure;
import edu.curtin.oose.assignment2.probe.command.Move;

public class ProbeList
{
    private Map<String, Probe> probes;
    private List<NextDayObserver> nextDayObservers;
    private List<DiagnosticObserver> writeObservers;
    private int currentSol;

    public ProbeList()
    {
        this.probes = new HashMap<>();
        this.nextDayObservers = new LinkedList<>();
        this.writeObservers = new LinkedList<>();
        this.currentSol = 0;
    }

    public void addProbe(String name, Probe probe)
    {
        // Add new probe
        this.probes.put(name, probe);

        // Add as observer
        this.nextDayObservers.add(probe);
        this.writeObservers.add(probe);
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
        List<Command> moves = generateMoveCommands(lat, longi, newLat, newLongi,
            maxDegree);

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
        notifyNextDay();
        this.currentSol++;

        for(Probe p : probes.values())
        {
            p.sendCommand();
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
        double targetLong, double maxDegree
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

    private void notifyNextDay()
    {
        for(NextDayObserver m : nextDayObservers)
        {
            m.incrementSol();
        }
    }

    private void notifyWrite(DiagnosticWriter w)
    {
        for(DiagnosticObserver o : this.writeObservers)
        {
            o.write(w);
        }
    }
}
