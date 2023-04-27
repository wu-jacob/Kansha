package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import exceptions.DuplicateDateException;
import exceptions.NoJournalEntryException;

import java.util.ArrayList;
import java.util.Objects;

// Represents a journal with a list of journal entries
public class Journal implements Writable {
    private final ArrayList<JournalEntry> entries; // a list of journal entries

    // EFFECTS: constructs a new journal with a list of journal entries
    public Journal() {
        this.entries = new ArrayList<>();
    }

    // EFFECTS: returns the journal entry that was written on the given date
    public JournalEntry getEntriesDate(String date) throws NoJournalEntryException {
        for (JournalEntry entry: this.entries) {
            if (Objects.equals(entry.getDate(), date)) {
                EventLog.getInstance().logEvent(
                        new Event("Found journal entry that was written on " + date + " ."));
                return entry;
            }
        }
        throw new NoJournalEntryException();
    }

    // EFFECTS: returns an array list with all journal entries
    public ArrayList<JournalEntry> getAllEntries() {
        return entries;
    }

    // EFFECTS: returns the dates of all entries as an array list of strings
    public ArrayList<String> getAllDates() {
        ArrayList<String> totalDates = new ArrayList<>();
        for (JournalEntry entry : entries) {
            totalDates.add(entry.getDate());
        }
        return totalDates;
    }

    // MODIFIES: this
    // EFFECTS: adds given entry to the list of entries in the journal
    public void addJournalEntry(JournalEntry entry) throws DuplicateDateException {
        for (JournalEntry e : entries) {
            if (e.getDate().equals(entry.getDate())) {
                throw new DuplicateDateException();
            }
        }
        this.entries.add(entry);
        EventLog.getInstance().logEvent(new Event("Journal entry added."));
    }

    // MODIFIES: this
    // EFFECTS: deletes the journal entry in the journal that corresponds to this date, if
    // no journal entry has the given date, it does nothing
    public void deleteJournalEntryDate(String date) {
        for (JournalEntry entry: this.entries) {
            if (Objects.equals(entry.getDate(), date)) {
                this.entries.remove(entry);
                break;
            }
        }
    }

    // EFFECTS: returns the number of entries in the journal
    public int journalSize() {
        return this.entries.size();
    }

    // EFFECTS: converts journal to a json object and returns the json object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("entries", entriesToJson());
        return json;
    }

    // EFFECTS: returns entries in this journal as a JSON array
    private JSONArray entriesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (JournalEntry e : entries) {
            jsonArray.put(e.toJson());
        }

        return jsonArray;
    }
}
