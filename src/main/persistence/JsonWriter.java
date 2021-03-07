package persistence;


import org.json.JSONArray;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// A file writer that saves objects in a JSON string format
public class JsonWriter {
    private static final int INDENT = 4;    // level of indentation for string formatting
    private final PrintWriter writer;       // prints objects to text-output stream

    // EFFECTS: constructs a new file writer that writes to a given path
    public JsonWriter(String path) throws FileNotFoundException {
        writer = new PrintWriter(new File(path));
    }

    // EFFECTS: writes string representation of JSON array to file
    public void save(JSONArray decks) {
        String s = decks.toString(INDENT);
        writer.print(s);
        writer.close();
    }
}