package edu.curtin.oose.assignment2.messageparser;

import java.util.LinkedList;
import java.util.List;

import edu.curtin.oose.assignment2.probe.ProbeList;
import edu.curtin.oose.assignment2.probe.ProbeListException;

/**
 * MessageParser handles the parsing of messages being sent from Earth to the
 * satellite (simulation). Once parsed, the probes can then be instructed by the
 * satellite.
 */
public class MessageParser
{
    private String[] split;
    private String probeName;

    /**
     * Constructor for MessageParser.
     */
    public MessageParser()
    {
        this.split = null;
        this.probeName = null;
    }

    /**
     * Splits message into array and then initialises MessageParser object if
     * length is above minimum.
     *
     * @param message Message to be split
     * @throws IllegalArgumentException when message is null
     * @throws MessageParserException   when length is below minimum
     */
    public void splitMessage(String message) throws MessageParserException
    {
        if(message == null)
        {
            throw new IllegalArgumentException("Parameter is null.");
        }
        if(message.isBlank())
        {
            // Let program handle blank messages from Earth
            throw new MessageParserException("Parameter is blank.");
        }

        String[] temp = message.split(" ");

        // Array requires at least two elements
        // temp[0]: probe name
        // temp[1]: instruction
        //
        // temp[2]: args for instruction (not required for history and status)
        if(temp.length < 2)
        {
            throw new MessageParserException(
                "Number of arguments in the message is invalid.");
        }

        // Store into object
        this.split = temp;
        this.probeName = this.split[0];
    }

    /**
     * Parses the message and sends to the satellite (simulation).
     *
     * @param probeList Object that handles all probes.
     * @throws IllegalStateException  when message has not been split
     * @throws MessageParserException when probeName does not exist or
     *                                instruction is invalid.
     */
    public void sendToSatellite(ProbeList probeList)
        throws MessageParserException
    {
        // Check if a message has been split first
        if(this.split == null || this.probeName == null)
        {
            throw new IllegalStateException(
                "Function was called before splitting message.");
        }

        // Check if probe exists in list
        if(!probeList.hasProbe(this.probeName))
        {
            throw new MessageParserException("Probe provided does not exist.");
        }

        // Check what instruction to send
        switch (this.split[1])
        {
            case "move":
                parseMoveMessage(probeList);
                break;
            case "measure":
                parseMeasureMessage(probeList);
                break;
            case "status":
                parseStatusMessage(probeList);
                break;
            case "history":
                parseHistoryMessage(probeList);
                break;
            default:
                throw new MessageParserException("Instruction does not exist.");
        }

        // Clear split and probeName
        this.split = null;
        this.probeName = null;
    }

    /**
     * Parses a move message from Earth.
     *
     * @param probeList Object that handles all probes.
     * @throws MessageParserException message is not in appropriate format,
     *                                numbers could not be converted or probe
     *                                could not be found
     */
    private void parseMoveMessage(ProbeList probeList)
        throws MessageParserException
    {
        // Format:
        // split[0]: probe name
        // split[1]: "move"
        // split[2]: lattitude (number)
        // split[3]: longitude (number)
        if(this.split.length != 4)
        {
            throw new MessageParserException(
                "Move instruction is not in the appropriate format.");
        }

        try
        {
            // Try parsing coordinates
            double newLat = Double.parseDouble(this.split[2]);
            double newLong = Double.parseDouble(this.split[3]);

            // Parsed successfully from this point on
            // Send the instruction (simulation)
            probeList.instructMove(probeName, newLat, newLong);
        }
        catch(NumberFormatException nfe)
        {
            throw new MessageParserException(
                "Move instruction is invalid. Coordinates could not be casted into a number.",
                nfe);
        }
        catch(ProbeListException ple)
        {
            // Probe could not be found in list anyway
            throw new MessageParserException(ple.getMessage(), ple);
        }
    }

    /**
     * Parses a measure message from Earth.
     *
     * @param probeList Object that handles all probes.
     * @throws MessageParserException when message not in appropriate format,
     *                                number could not be casted and probe could
     *                                not be found
     */
    private void parseMeasureMessage(ProbeList probeList)
        throws MessageParserException
    {
        // Format:
        // split[0]: probe name
        // split[1]: "measure"
        // split[2]: quantity to be measured
        // split[in-between]: more quantities if required
        // split[last]: number of measure commands (number)
        if(this.split.length < 4)
        {
            throw new MessageParserException(
                "Measure instruction is not in the appropriate format.");
        }

        // Quantities to be measured after first two elements
        List<String> quantities = new LinkedList<>();
        for(int i = 2; i < this.split.length - 1; i++)
        {
            quantities.add(this.split[i]);
        }

        try
        {
            // Try parsing total number of commands
            int lastIndex = this.split.length - 1;
            int num = Integer.parseInt(this.split[lastIndex]);

            // Parsed successfully from this point on
            // Send the instruction (simulation)
            probeList.instructMeasure(probeName, quantities, num);
        }
        catch(NumberFormatException nfe)
        {
            throw new MessageParserException(
                "Measure instruction is invalid. Must provide number of days.",
                nfe);
        }
        catch(ProbeListException ple)
        {
            // Probe could not be found in list anyway
            throw new MessageParserException(ple.getMessage(), ple);
        }
    }

    /**
     * Parses status message from Eerth.
     *
     * @param probeList Object that handles all probes.
     * @throws MessageParserException when message has unnecessary arguments or
     *                                probe could not be found.
     */
    private void parseStatusMessage(ProbeList probeList)
        throws MessageParserException
    {
        checkUnnecessaryArgs(); // throws exception

        try
        {
            probeList.printProbeStatus(this.probeName);
        }
        catch(ProbeListException ple)
        {
            // Probe could not be found
            throw new MessageParserException(ple.getMessage(), ple);
        }
    }

    /**
     * Parse history message from Earth.
     *
     * @param probeList Object that handles all probes.
     * @throws MessageParserException when message has unnecessary arguments or
     *                                probe could not be found.
     */
    private void parseHistoryMessage(ProbeList probeList)
        throws MessageParserException
    {
        checkUnnecessaryArgs(); // throws exception

        try
        {
            probeList.printProbeHistory(this.probeName);
        }
        catch(ProbeListException ple)
        {
            // Probe could not be found in list anyway
            throw new MessageParserException(ple.getMessage(), ple);
        }
    }

    /**
     * Checks whether the message contains more arguments than required.
     *
     * @throws MessageParserException when message does not have required length
     *                                when split
     */
    private void checkUnnecessaryArgs() throws MessageParserException
    {
        // If message contains extra arguments that are not required
        if(this.split.length != 2)
        {
            throw new MessageParserException(
                "Message contains unnecessary arguments.");
        }
    }
}
