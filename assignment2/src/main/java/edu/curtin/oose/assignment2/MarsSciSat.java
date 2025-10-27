package edu.curtin.oose.assignment2;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import edu.curtin.oose.assignment2.diagnostic.DiagnosticWriter;
import edu.curtin.oose.assignment2.diagnostic.DiagnosticWriterException;
import edu.curtin.oose.assignment2.messageparser.MessageParser;
import edu.curtin.oose.assignment2.messageparser.MessageParserException;
import edu.curtin.oose.assignment2.probe.Probe;
import edu.curtin.oose.assignment2.probe.ProbeList;
import edu.curtin.oose.assignment2.probe.ProbeListFactory;

/**
 * Main file for Assignment2. Runs the loop until terminated by the user or due
 * to error.
 *
 * @author Joshua Orbon 20636948
 */
public class MarsSciSat
{
    private static Logger logger = Logger.getLogger(MarsSciSat.class.getName());

    private static final double MAX_DISTANCE_ROVER = 0.004;
    private static final double MAX_DISTANCE_DRONE = 0.018;

    public static void main(String[] args)
    {
        CommsGenerator inp = new CommsGenerator(123); // Seed for the random
        // inp.setErrorProbability(0.0);

        // Initialise necessary objects
        MarsSciSat marsSciSat = new MarsSciSat();
        List<String> messages = new LinkedList<>();

        // Create diagnostic file
        DiagnosticWriter diag = new DiagnosticWriter();
        try
        {
            diag.createFile();
        }
        catch(DiagnosticWriterException dwe)
        {
            System.out.println(dwe.getMessage());
            logger.severe(() -> "File for diagnostics file was not created.\n"
                + dwe);
        }

        // Grab locations of listed probes
        marsSciSat.storeMessages(messages, inp);

        // Create new probes using initial messages
        List<Probe> probes = new LinkedList<>();
        marsSciSat.initialiseProbes(probes, messages);

        logger.info(() -> "Probes to be instructed have been gathered.");

        // Clear messages
        messages.clear();

        // Create ProbeList object and list of probes
        ProbeList probeList = new ProbeListFactory().createProbeList(probes);

        logger.info(() -> "Probes now stored in ProbeList object.");

        // Add observers
        for(Probe p : probes)
        {
            probeList.addDiagnosticObserver(p);
        }

        logger.info(() -> "Added observers to ProbeList.");

        // Run loop
        MessageParser parser = new MessageParser();
        try
        {
            logger.info(() -> "Now running event loop");

            // Run loop
            while(System.in.available() == 0)
            {
                // Wait 1 second
                try
                {
                    Thread.sleep(1000);
                }
                catch(InterruptedException e)
                {
                    throw new AssertionError(e);
                }

                marsSciSat.storeMessages(messages, inp);

                // Mark start of Martian day
                System.out.println("---");

                // Parse the messages
                for(String m : messages)
                {
                    // Print message from Earth
                    System.out.println("\033[34;1m" + m + "\033[m");

                    marsSciSat.parseMessage(m, probeList, parser);
                }

                // Clear list
                messages.clear();

                // Instruct probes
                probeList.sendCommands();

                // Mark end of Martian day
                System.out.println("---");

                // Write to diagnostics file
                probeList.writeDiagnostics(diag);
            }
        }
        catch(IOException e)
        {
            System.out.println("Error reading user input.");

            logger.severe(() -> "Program terminated due to I/O error.\n" + e);
            // Terminate program
        }
        catch(DiagnosticWriterException dwe)
        {
            System.out.println(dwe.getMessage());

            logger.severe(() -> "Diagnostics could not be written.\n" + dwe);
            // Terminate program
        }
    }

    public MarsSciSat()
    {
    }

    /**
     * Initialises list of probes from initial set of messages sent by
     * CommsGenerator object.
     *
     * @param probes   Used to store probes
     * @param messages Initial messages from CommsGenerator
     * @throws IllegalArgumentException when parameters are null or messages
     *                                  list contains null
     */
    public void initialiseProbes(List<Probe> probes, List<String> messages)
    {
        if(probes == null || messages == null)
        {
            throw new IllegalArgumentException("Parameter(s) are null.");
        }

        if(messages.contains(null))
        {
            throw new IllegalArgumentException(
                "List of messages contains null.");
        }

        // Initial messages should be of appropriate format
        for(String s : messages)
        {
            // Split message
            String[] tempSplit = s.split(" ", 4);

            // Clean probe attributes
            String probeName = tempSplit[0];
            double lat = Double.parseDouble(tempSplit[2]);
            double longi = Double.parseDouble(tempSplit[3]);

            double maxDistance = Double.NEGATIVE_INFINITY;
            if(probeName.contains("drone"))
            {
                maxDistance = MAX_DISTANCE_DRONE;
            }
            else if(probeName.contains("rover"))
            {
                maxDistance = MAX_DISTANCE_ROVER;
            }

            // Add probe to list
            Probe p = new Probe(probeName, lat, longi, maxDistance);
            probes.add(p);
        }
    }

    /**
     * Use CommsGenerator object to generate messages from Earth and store in a
     * List.
     *
     * @param messages List to store message Strings
     * @param comm     Object used to generate messages
     * @throws IllegalStateException Messages were not cleared beforehand
     */
    public void storeMessages(List<String> messages, CommsGenerator comm)
    {
        if(!messages.isEmpty())
        {
            logger.severe(
                () -> "Messages were not cleared prior to running this function.");

            throw new IllegalStateException("Messages were not cleared.");
        }

        String msg = comm.nextMessage();
        while(msg != null)
        {
            // Add messages to list
            messages.add(msg);

            // Grab next message
            msg = comm.nextMessage();
        }

        logger.info(() -> "Messages generated and stored.");
    }

    /**
     * Use MessageParser object to parse a message from Earth.
     *
     * @param message   Message 'sent' from Earth
     * @param probeList Object that contains list of probes
     * @param parser    Object that parses the message
     */
    public void parseMessage(
        String message, ProbeList probeList, MessageParser parser
    )
    {
        try
        {
            // Split message
            parser.splitMessage(message);

            // Send message to satellite
            parser.sendToSatellite(probeList);
        }
        catch(MessageParserException mpe)
        {
            // Format error
            String errorMessage = "TO EARTH: MESSAGE ERROR \"" + mpe
                .getMessage() + "\"";

            // Simply print error to screen (red)
            System.out.println("\033[31;1m" + errorMessage + "\033[m");

            // Log exception
            logger.warning(() -> "Generated message '" + message
                + "' could not be parsed.\n" + mpe);
        }
    }
}
