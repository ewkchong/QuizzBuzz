package persistence;


import org.json.JSONArray;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Writer {
    private static final int INDENT = 4;
    private PrintWriter writer;

    public Writer(String path) throws FileNotFoundException {
        writer = new PrintWriter(new File(path));
    }

    public void save(JSONArray decks) {
        String s = decks.toString(INDENT);
        writer.print(s);
        writer.close();
    }
}