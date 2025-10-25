package edu.curtin.oose.assignment2.probe.command;

import java.util.List;

import edu.curtin.oose.assignment2.probe.Probe;

/**
 * Interface for MarsSciSat commands 'sent' to probes.
 *
 * @author Joshua Orbon 20636948
 */
public interface Command
{
    /**
     * Prints the command, simulating the process of communication to the probe.
     *
     * @param probeName Name of the probe
     */
    void printCommand(String probeName);

    /**
     * Simulates the execution of the command using the Probe. Could simply be
     * using the Probe's attributes for printing to the screen.
     *
     * @param probe The Probe object that has 'received' the command
     */
    void execute(Probe probe);

    /**
     * A function for Probe object to use to format the command into a String
     * for storing in history.
     *
     * @param sol
     * @return formatted String detailing the command's execution
     */
    String save(int sol);

    /**
     * A getter function for the command's parameters.
     *
     * @return List of data of type String
     */
    List<String> getData();
}
