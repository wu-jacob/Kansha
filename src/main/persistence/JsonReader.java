package persistence;

import exceptions.DuplicateDateException;
import exceptions.InvalidDateException;
import exceptions.InvalidScoreException;
import model.Journal;
import model.JournalEntry;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

// Represents a reader that reads Journal from JSON data stored in file
public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads Journal from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Journal read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseJournal(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Journal from JSON object and returns it
    private Journal parseJournal(JSONObject jsonObject) throws IOException {
        Journal journal = new Journal();
        addEntries(journal, jsonObject);
        return journal;
    }

    // MODIFIES: journal
    // EFFECTS: parses entries from JSON object and adds them to Journal
    private void addEntries(Journal journal, JSONObject jsonObject) throws IOException {
        JSONArray jsonArray = jsonObject.getJSONArray("entries");
        for (Object json : jsonArray) {
            JSONObject nextEntry = (JSONObject) json;
            addEntry(journal, nextEntry);
        }
    }

    // MODIFIES: journal
    // EFFECTS: parses entry from JSON object and adds it to Journal
    private void addEntry(Journal journal, JSONObject jsonObject) throws IOException {
        String date = jsonObject.getString("date");
        String title = jsonObject.getString("title");
        int score = jsonObject.getInt("score");
        ArrayList<String> gratitudeStatements = new ArrayList<>();
        addStatements(gratitudeStatements, jsonObject);
        try {
            JournalEntry newEntry = new JournalEntry(date, title, score, gratitudeStatements);
            journal.addJournalEntry(newEntry);
        } catch (InvalidDateException | InvalidScoreException | DuplicateDateException e) {
            throw new IOException("File corrupted");
        }
    }

    // EFFECTS: parses given json object and adds it to list of gratitude statements
    private void addStatements(ArrayList<String> statements, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("statements");
        for (Object json : jsonArray) {
            statements.add(json.toString());
        }
    }
}
