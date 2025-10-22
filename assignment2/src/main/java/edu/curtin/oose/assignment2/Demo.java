package edu.curtin.oose.assignment2;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

import edu.curtin.oose.assignment2.probe.Probe;

/**
 * Use this code to get started on Assignment 2. You are free to modify or
 * replace this file as needed (to fulfil the assignment requirements, of
 * course).
 */
public class Demo
{
    public static void main(String[] args)
    {
        // CommsGenerator inp = new CommsGenerator();
        CommsGenerator inp = new CommsGenerator(123); // Seed for the random
        // number generator
        // inp.setErrorProbability(0.0);

        ProbeList probeList = new ProbeList();
        Demo marsSciSat = new Demo();
        LinkedList<String> messages = new LinkedList<>();

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

            // Add probe to satellite
            Probe p = new Probe(probeName, lat, longi);
            probeList.addProbe(probeName, p);
        }

        // Clear messages
        messages.clear();

        try
        {
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
                    marsSciSat.parseMessage(m, probeList);
                }

                // Clear list
                messages.clear();

                // Instruct probes
                probeList.sendCommands();
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

    public void getMessages(LinkedList<String> messages, CommsGenerator comm)
    {
        // Grab initial message
        System.out.println("---");
        String msg = comm.nextMessage();

        while(msg != null)
        {
            // Print message from Earth
            System.out.println("\033[34;1m" + msg + "\033[m");

            // Add messages to list
            messages.add(msg);

            // Grab next message
            msg = comm.nextMessage();
        }

        System.out.println("---");
    }

    public void parseMessage(String message, ProbeList probeList)
    {
        // Split message
        String[] messageSplit = message.split(" ");

        // Format
        String probe = messageSplit[0];
        String command = messageSplit[1];

        switch (command)
        {
            case "status":
                probeList.getProbeStatus(probe);
                break;
            case "move":
                if(messageSplit.length == 4)
                {
                    double newLat = Double.parseDouble(messageSplit[2]);
                    double newLong = Double.parseDouble(messageSplit[3]);
                    probeList.moveProbe(probe, newLat, newLong);
                }
                break;
            case "measure":
                // Quantities to be measured after first two elements
                List<String> quantities = new LinkedList<>();

                for(int i = 2; i < messageSplit.length - 1; i++)
                {
                    quantities.add(messageSplit[i]);
                }

                // Total number of commands
                int num = Integer.parseInt(messageSplit[messageSplit.length - 1]);
                probeList.instructMeasure(probe, quantities, num);

                break;
            case "history":
                probeList.getProbeHistory(probe);
                break;
        }
    }
}
