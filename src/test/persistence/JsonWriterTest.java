package persistence;

import exceptions.DuplicateDateException;
import exceptions.InvalidDateException;
import exceptions.InvalidScoreException;
import model.Journal;
import model.JournalEntry;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Test for the JsonWriter class
public class JsonWriterTest extends JsonTest {
    @Test
    void testWriterInvalidFile() {
        try {
            Journal journal = new Journal();
            JsonWriter writer = new JsonWriter("./data/\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyJournal() {
        try {
            Journal journal = new Journal();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyJournal.json");
            writer.open();
            writer.write(journal);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyJournal.json");
            journal = reader.read();
            assertEquals(0, journal.journalSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralJournal() {
        try {
            JournalEntry testEntry1 = new JournalEntry("01/01/2001","Test Title 1", 1, makeStatements());
            JournalEntry testEntry2 = new JournalEntry("02/29/2004", "Test Title 2",1, makeStatements());
            JournalEntry testEntry3 = new JournalEntry("12/31/2023","Test Title 3", 10, makeStatements());
            Journal journal = new Journal();
            journal.addJournalEntry(testEntry1);
            journal.addJournalEntry(testEntry2);
            journal.addJournalEntry(testEntry3);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralJournal.json");
            writer.open();
            writer.write(journal);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterGeneralJournal.json");
            journal = reader.read();
            ArrayList<JournalEntry> entries = journal.getAllEntries();
            assertEquals(3, entries.size());
            checkEntry("01/01/2001", "Test Title 1", 1, makeStatements(), entries.get(0));
            checkEntry("02/29/2004", "Test Title 2", 1, makeStatements(), entries.get(1));
            checkEntry("12/31/2023", "Test Title 3", 10, makeStatements(), entries.get(2));
        } catch (IOException | DuplicateDateException | InvalidDateException | InvalidScoreException e) {
            fail("Exception should not have been thrown");
        }
    }
}
