package edu.curtin.oose.assignment2.probe;

import java.util.List;

/**
 * Factory for ProbeList, adds list of Probe objects.
 *
 * @author JoshuaOrbon 20636948
 */
public class ProbeListFactory
{
    /**
     * Creates ProbeList and object and adds list of probes.
     *
     * @param probes List of probes to be added into object
     * @return ProbeList object
     */
    public ProbeList createProbeList(List<Probe> probes)
    {
        ProbeList probeList = new ProbeList();

        // Add probes to list
        for(Probe p : probes)
        {
            probeList.addProbe(p);
        }

        return probeList;
    }

}
