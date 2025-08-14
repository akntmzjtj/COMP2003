package edu.curtin.productfinder;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

class CatalogueTest
{
    @TempDir
    static Path tempDir;

    @Test
    void integrationTest() throws IOException, CatalogueFormatException
    {
        var catPath = tempDir.resolve("testcat.txt");
        Files.write(
            catPath,
            List.of(
                "All:Cat1",
                "All:Cat2",
                "All:Cat3",
                "Cat1:Prod1:10:1",
                "Cat2:Prod2:20:2",
                "Cat3:Prod3:30:3",
                "Cat2:Cat2A",
                "Cat2A:Prod4:40:4"
            ));

        var rootItem = new CatalogueFileIO().readCatalogue(catPath.toString());

        assertEquals(
            300.0,
            rootItem.calcStockValue(),
            0.00001,
            "Stock value should be 10x1 + 20x2 + 30x3 + 40x4");

        var searchResults = rootItem.findProducts("od", 15.0, 35.0);
        Collections.sort(searchResults);
        String line0 = searchResults.get(0);
        String line1 = searchResults.get(1);

        assertTrue(
            searchResults.size() == 2
            && line0.contains("20") && line0.contains("Prod2")
            && line1.contains("30") && line1.contains("Prod3"),
            "Searching for term 'od', with 15 <= price <= 30 should return '$20 -- Prod2' and '$30 -- Prod3'");

        // var originalOut = System.out;
        // var outputBuf = new ByteArrayOutputStream();
        // try
        // {
        //     System.setOut(new PrintStream(outputBuf));
        //     rootItem.display();
        // }
        // finally
        // {
        //     System.setOut(originalOut);
        // }
        //
        // var outputLines = outputBuf.toString().split("\n");
        // assertEquals("All:", outputLines[0]);
        // assertEquals("    Cat1:", outputLines[1]);
        // assertEquals("        Prod1
    }
}
