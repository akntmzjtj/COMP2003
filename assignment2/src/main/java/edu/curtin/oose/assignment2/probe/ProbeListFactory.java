package edu.curtin.oose.assignment2.probe;

import java.util.List;

/**
 * Factory for ProbeList, adds list of Probe objects.
 *
 * @author JoshuaOrbon 20636948
 */
public class ProbeListFactory
{
    public ProbeList createProbeList(List<Probe> probes)
    {
        ProbeList probeList = new ProbeList();

        // Add probes to list
        for(Probe p : probes)
        {
            probeList.addProbe(p.getName(), p);
        }

        return probeList;
    }

}
