package edu.curtin.oose.assignment2.diagnostic;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Class for handling writing diagnostics to a text file. Can be changed to an
 * interface, allowing for multiple methods of writing diagnostics.
 *
 * @author Joshua Orbon 20636948
 */
public class DiagnosticWriter
{
    private static final String FILENAME = "diagnostics.txt";

    public DiagnosticWriter()
    {
    }

    /**
     * Creates a file to write diagnostic strings to.
     *
     * @throws DiagnosticWriterException when I/O error occurs
     */
    public void createFile() throws DiagnosticWriterException
    {
        try
        {
            File file = new File(FILENAME);
            if(file.exists())
            {
                file.delete();
            }

            file.createNewFile();
        }
        catch(IOException io)
        {
            throw new DiagnosticWriterException(
                "Failed to create diagnostic file.", io);
        }
    }

    /**
     * Appends a string to the diagnostics file.
     *
     * @param s String to be appended
     * @throws IllegalStateException     when file to be written to does not
     *                                   exist
     * @throws DiagnosticWriterException when I/O error occurs
     */
    public void append(String s) throws DiagnosticWriterException
    {
        if(!this.exists())
        {
            throw new IllegalStateException("Diagnostic file not created.");
        }

        try(Writer w = new FileWriter(FILENAME, true))
        {
            w.write(s);
        }
        catch(IOException io)
        {
            throw new DiagnosticWriterException(
                "Exception occurred while appending to file.", io);
        }
    }

    /**
     * Checks whether diagnostics file exist.
     *
     * @return true when file exists
     */
    public boolean exists()
    {
        File file = new File(FILENAME);
        return file.exists();
    }
}
