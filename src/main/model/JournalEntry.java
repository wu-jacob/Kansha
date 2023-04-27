package model;

import exceptions.InvalidDateException;
import exceptions.InvalidScoreException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a journal entry with a date, title, score, and list of gratitude statements
public class JournalEntry implements Writable {
    private final Date date;                   // the date the journal entry was written (YYYY/MM/DD)
    private final String title;                     // the title of the journal entry
    private final int score;                        // a score of how good the day was (1-10)
    private final ArrayList<String> gratitudeStatements; // a list of gratitude statements

    // EFFECTS: constructs a new journal entry with the current date, given title, score, and
    // list of gratitude statements
    public JournalEntry(String date, String title, int score, ArrayList<String> gratitudeStatements)
            throws InvalidDateException, InvalidScoreException {
        if (score < 1 || score > 10) {
            throw new InvalidScoreException();
        }
        this.date = new Date(date);
        this.title = title;
        this.score = score;
        this.gratitudeStatements = gratitudeStatements;
    }

    // EFFECTS: returns the date the journal entry was written on
    public String getDate() {
        return this.date.dateString();
    }

    // EFFECTS: returns the title of the journal entry
    public String getTitle() {
        return title;
    }

    // EFFECTS: returns the score of the journal entry
    public int getScore() {
        return score;
    }

    // EFFECTS: returns an array list of strings containing the gratitude statements
    // written in this journal entry
    public ArrayList<String> getGratitudeStatements() {
        return gratitudeStatements;
    }

    // EFFECTS: converts journal entry to json object and returns the json object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("date", date.dateString());
        json.put("title", title);
        json.put("score", score);
        json.put("statements", statementsToJson());
        return json;
    }

    // EFFECTS: converts gratitude statements to json array
    private JSONArray statementsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (String s : gratitudeStatements) {
            jsonArray.put(s);
        }

        return jsonArray;
    }
}