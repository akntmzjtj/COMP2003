package edu.curtin.oose.assignment2.probe;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.curtin.oose.assignment2.diagnostic.DiagnosticObserver;
import edu.curtin.oose.assignment2.diagnostic.DiagnosticWriter;
import edu.curtin.oose.assignment2.diagnostic.DiagnosticWriterException;
import edu.curtin.oose.assignment2.probe.command.Command;
import edu.curtin.oose.assignment2.probe.command.Measure;
import edu.curtin.oose.assignment2.probe.command.Move;

/**
 * Maintains a list of Probe objects and observers. It generates the Command
 * objects and simulates sending these commands to the Probes.
 *
 * @author Joshua Orbon 20636948
 */
public class ProbeList
{
    private Map<String, Probe> probes;
    private List<DiagnosticObserver> writeObservers;
    private int currentSol;

    /**
     * Constructor for ProbeList.
     */
    public ProbeList()
    {
        this.probes = new HashMap<>();
        this.writeObservers = new LinkedList<>();
        this.currentSol = 0;
    }

    /**
     * Adds probe to map.
     *
     * @param name  Name of probe
     * @param probe Probe object
     * @throws IllegalArgumentException when Probe object is null
     */
    public void addProbe(String name, Probe probe)
    {
        if(probe == null)
        {
            throw new IllegalArgumentException("Parameter is null.");
        }

        // Add new probe
        this.probes.put(name, probe);
    }

    /**
     * Add DiagnosticObserver object to list of observers.
     *
     * @param observer DiagnosticObserver object
     * @throws IllegalArgumentException when Parameter is null
     */
    public void addDiagnosticObserver(DiagnosticObserver observer)
    {
        if(observer == null)
        {
            throw new IllegalArgumentException("Parameter is null.");
        }

        this.writeObservers.add(observer);
    }

    /**
     * Remove DiagnosticObserver object from list of observers.
     *
     * @param observer DiagnosticObserver object
     */
    public void removeDiagnosticObserver(DiagnosticObserver observer)
    {
        this.writeObservers.remove(observer);
    }

    /**
     * Check if name of probe exists in map.
     *
     * @param probeName Name of probe
     * @return true if probe exists
     */
    public boolean hasProbe(String probeName)
    {
        return this.probes.containsKey(probeName);
    }

    /**
     * Instructs given probe to a new coordinate.
     *
     * @param probeName Name of probe
     * @param newLat    New lattitude
     * @param newLongi  New longitude
     * @throws ProbeListException when probe is not in map
     */
    public void instructMove(String probeName, double newLat, double newLongi)
        throws ProbeListException
    {
        Probe probe = probes.get(probeName);
        if(probe == null)
        {
            throw new ProbeListException("Probe not found.");
        }

        double lat = probe.getLattitude();
        double longi = probe.getLongitude();

        // Calculate move commands
        List<Command> moves = generateMoveCommands(lat, longi, newLat, newLongi,
            probe.getMaxDistance());

        // Send move commands to probe
        probe.storeMoves(moves);
    }

    /**
     * Instructs a given probe to measure a list of quantities.
     *
     * @param probeName  Name of probe
     * @param quantities List of quantities to be measured
     * @param num        Number of times the quantities should be measured
     * @throws ProbeListException when probe is not in map
     */
    public void instructMeasure(
        String probeName, List<String> quantities, int num
    ) throws ProbeListException
    {
        Probe probe = probes.get(probeName);
        if(probe == null)
        {
            throw new ProbeListException("Probe not found.");
        }

        // Generate measure commands
        List<Command> measureList = generateMeasureCommands(quantities, num);

        // Store measure instructions
        probe.storeMeasure(measureList);
    }

    /**
     * Prints probe's current status.
     *
     * @param probeName Name of probe
     * @throws ProbeListException when probe is not in map
     */
    public void printProbeStatus(String probeName) throws ProbeListException
    {
        Probe probe = probes.get(probeName);
        if(probe == null)
        {
            throw new ProbeListException("Probe not found.");
        }

        // Print status
        System.out.printf("TO EARTH: %s", probe.getStatus());
    }

    /**
     * Prints probe's current history.
     *
     * @param probeName Name of probe
     * @throws ProbeListException when probe is not in map
     */
    public void printProbeHistory(String probeName) throws ProbeListException
    {
        Probe probe = probes.get(probeName);
        if(probe == null)
        {
            throw new ProbeListException("Probe not found.");
        }

        // Print history
        List<String> probeHistory = probe.getHistory();

        System.out.println("TO EARTH: " + probeName.toUpperCase()
            + " ACTIVITIES:");
        for(String s : probeHistory)
        {
            System.out.println("    " + s);
        }
    }

    /**
     * Send commands to physical probe (simply prints it for simulation).
     *
     * @throws IllegalStateException map contains null
     */
    public void sendCommands()
    {
        // Next day for probes
        this.currentSol++;

        // It should not contain null as it is handled in addProbe() but just in
        // case
        if(probes.containsValue(null))
        {
            throw new IllegalStateException("Contains null in container.");
        }

        for(Probe p : probes.values())
        {
            p.sendCommand(this.currentSol);
        }
    }

    /**
     * Write diagnostics of each probe.
     *
     * @param w DiagnosticWriter object, handles writing the string
     */
    public void writeDiagnostics(DiagnosticWriter w)
        throws DiagnosticWriterException
    {
        if(w == null)
        {
            throw new IllegalArgumentException(
                "Null object used to write diagnostics.");
        }

        w.append("SOL " + this.currentSol + ":" + "\n");

        // Write probes to diagnostic
        notifyWrite(w);
    }

    /**
     * Generate the Move objects to be stored in a Probe object.
     *
     * @param currentLat  Probe's current lattitude
     * @param currentLong Probe's current longitude
     * @param targetLat   Probe's target lattitude
     * @param targetLong  Probe's target longitude
     * @param maxDistance Probe's max distance it can travel in a day
     * @return List of Command objects (Move)
     */
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

    /**
     * Generate the Measure objects to be stored in a Probe object.
     *
     * @param quantities List of quantities to be measured
     * @param num        Number of commands
     * @return List of Command objects (Measure)
     * @throws IllegalArgumentException when List is null or contains null
     */
    private List<Command> generateMeasureCommands(
        List<String> quantities, int num
    )
    {
        if(quantities == null)
        {
            throw new IllegalArgumentException("Parameter is null.");
        }

        if(quantities.contains(null))
        {
            throw new IllegalArgumentException("List contains null.");
        }

        List<Command> out = new LinkedList<>();
        for(int i = 0; i < num; i++)
        {
            out.add(new Measure(quantities));
        }

        return out;
    }

    /**
     * Notify observers
     *
     * @param w DiagnosticWriter object, writes diagnostics
     */
    private void notifyWrite(DiagnosticWriter w)
        throws DiagnosticWriterException
    {
        // addDiagnosticWriter handles null but just in case
        if(this.writeObservers.contains(null))
        {
            throw new IllegalStateException("List of observers contains null.");
        }

        for(DiagnosticObserver o : this.writeObservers)
        {
            o.write(w);
        }
    }
}
