package edu.curtin.app;

import org.junit.jupiter.api.*;

import edu.curtin.oose.assignment2.Hello;

import static org.junit.jupiter.api.Assertions.*;

public class HelloTest
{
    @Test
    void testHello()
    {
        assertEquals("Hello world!", Hello.getHello());
    }
}
