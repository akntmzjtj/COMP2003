package edu.curtin.oose.assignment2.probe;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class DiagnosticWriter
{
    private static final String FILENAME = "diagnostics.txt";

    public DiagnosticWriter()
    {
    }

    public void createFile()
    {
        try
        {
            File file = new File(DiagnosticWriter.FILENAME);
            if(file.exists())
            {
                file.delete();
            }

            file.createNewFile();
        }
        catch(IOException io)
        {
            // TODO: handle exception
            System.out.println("Failed to create diagnostic file.");
        }
    }

    public boolean exists()
    {
        File file = new File(DiagnosticWriter.FILENAME);
        return file.exists();
    }

    public void append(String s)
    {
        if(!this.exists())
        {
            throw new IllegalStateException("Diagnostic file not created.");
        }

        try(Writer w = new FileWriter(DiagnosticWriter.FILENAME, true))
        {
            w.write(s);
        }
        catch(IOException io)
        {
            // TODO: handle exception
            System.out.println("Exception occurred while appending to file.");
        }
    }
}
