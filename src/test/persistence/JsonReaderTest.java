package persistence;

import exceptions.DuplicateDateException;
import model.Journal;
import model.JournalEntry;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Test for the JsonReader class
public class JsonReaderTest extends JsonTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Journal journal = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyJournal() {
        JsonReader reader = new JsonReader("./data/emptyJournal.json");
        try {
            Journal journal = reader.read();
            assertEquals(0, journal.journalSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralJournal() {
        JsonReader reader = new JsonReader("./data/generalJournal.json");
        try {
            Journal journal = reader.read();
            List<JournalEntry> entries = journal.getAllEntries();
            assertEquals(3, journal.journalSize());
            checkEntry("01/01/2001", "Test Title 1", 1, makeStatements(), entries.get(0));
            checkEntry("02/29/2004", "Test Title 2", 1, makeStatements(), entries.get(1));
            checkEntry("12/31/2023", "Test Title 3", 10, makeStatements(), entries.get(2));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderIOException() {
        JsonReader reader = new JsonReader("./data/duplicateDateJournal.json");
        try {
            Journal journal = reader.read();
            List<JournalEntry> entries = journal.getAllEntries();
            fail("Expected an IOException to be thrown");

        } catch (IOException e) {
            // pass
        }
        JsonReader reader2 = new JsonReader("./data/invalidDateJournal.json");
        try {
            Journal journal = reader2.read();
            List<JournalEntry> entries = journal.getAllEntries();
            fail("Expected an IOException to be thrown");

        } catch (IOException e) {
            // pass
        }
        JsonReader reader3 = new JsonReader("./data/invalidScoreJournal.json");
        try {
            Journal journal = reader3.read();
            List<JournalEntry> entries = journal.getAllEntries();
            fail("Expected an IOException to be thrown");

        } catch (IOException e) {
            // pass
        }
    }
}
