package edu.curtin.oose.assignment2;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

import edu.curtin.oose.assignment2.diagnostic.DiagnosticWriter;
import edu.curtin.oose.assignment2.messageparser.MessageParser;
import edu.curtin.oose.assignment2.messageparser.MessageParserException;
import edu.curtin.oose.assignment2.probe.Probe;
import edu.curtin.oose.assignment2.probe.ProbeList;

/**
 * Use this code to get started on Assignment 2. You are free to modify or
 * replace this file as needed (to fulfil the assignment requirements, of
 * course).
 */
public class Demo
{
    private static final double MAX_DISTANCE_ROVER = 0.004;
    private static final double MAX_DISTANCE_DRONE = 0.018;

    public static void main(String[] args)
    {
        // CommsGenerator inp = new CommsGenerator();
        CommsGenerator inp = new CommsGenerator(123); // Seed for the random
        // number generator
        // inp.setErrorProbability(0.0);

        // Initialise necessary objects
        Demo marsSciSat = new Demo();
        ProbeList probeList = new ProbeList();
        List<String> messages = new LinkedList<>();
        MessageParser parser = new MessageParser();

        // Create diagnostic file
        DiagnosticWriter diag = new DiagnosticWriter();
        diag.createFile();

        // Grab locations of listed probes
        marsSciSat.getMessages(messages, inp);

        // Create new probes using initial messages
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

            // Add probe to satellite
            Probe p = new Probe(probeName, lat, longi, maxDistance);
            probeList.addProbe(probeName, p);

            // Add probe as observer
            probeList.addDiagnosticObserver(p);
        }

        // Clear messages
        messages.clear();

        try
        {
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

                marsSciSat.getMessages(messages, inp);

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

                // Write to diagnostics file
                probeList.writeDiagnostics(diag);
            }
        }
        catch(IOException e)
        {
            System.out.println("Error reading user input");
        }
    }

    public Demo()
    {
    }

    public void getMessages(List<String> messages, CommsGenerator comm)
    {
        // Grab initial message
        System.out.println("---");
        String msg = comm.nextMessage();

        while(msg != null)
        {
            // Add messages to list
            messages.add(msg);

            // Grab next message
            msg = comm.nextMessage();
        }

        System.out.println("---");
    }

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
        }
    }
}
