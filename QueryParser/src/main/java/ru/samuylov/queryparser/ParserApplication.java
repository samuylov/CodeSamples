/**
 *
 */
package ru.samuylov.queryparser;

import org.apache.nifi.util.console.TextDevice;
import org.apache.nifi.util.console.TextDevices;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Main class for demonstration or query parsing
 *
 * @author samuylov
 */
public final class ParserApplication {
    private static final Logger log = LoggerFactory.getLogger(ParserApplication.class);

    ////////////////////////////////////////////// Main ////////////////////////////////////////////////////////
    public static void main(String[] args) {
        boolean exit = false;
        TextDevice textDevice = TextDevices.defaultTextDevice();

        while (!exit) {
            textDevice.printf("Enter search request:\n");

            processLine(textDevice);

            textDevice.printf("Parse next query? (Y - yes, something else to stop)\n");
            String answer = textDevice.readLine();
            exit = !"Y".equalsIgnoreCase(answer);
        }

    }

    ////////////////////////////////////////////// Implementation ////////////////////////////////////////////////////////
    private static void processLine(@NotNull TextDevice textDevice) {
        String sourceQuery = textDevice.readLine();
        try {
            SearchQuery query = new SearchQuery(sourceQuery);
            textDevice.printf("Parsing result:\n");
            textDevice.printf(query.toString()+ '\n');
        } catch (Exception e) {
            textDevice.printf("Search query parsing error: %s\n", e.getMessage());
            log.error(e.getMessage(), e);
        }
    }

}
