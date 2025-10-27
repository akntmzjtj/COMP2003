package edu.curtin.oose.assignment2.diagnostic;

/**
 * Interface for DiagnosticObserver. Must use DiagnosticWriter object to write
 * diagnostics. DiagnosticWriter can be changed into an interface to allow for
 * multiple methods of writing diagnostics.
 *
 * @author Joshua Orbon 20636948
 */
public interface DiagnosticObserver
{
    /**
     * Use a DiagnosticWriter object to write diagnostics.
     *
     * @param w DiagnosticWriter object used to handle writing
     */
    void write(DiagnosticWriter w) throws DiagnosticWriterException;
}
