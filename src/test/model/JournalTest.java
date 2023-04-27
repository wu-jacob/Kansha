package model;

import exceptions.DuplicateDateException;
import exceptions.InvalidDateException;
import exceptions.InvalidScoreException;
import exceptions.NoJournalEntryException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// Test for the JournalEntry class
public class JournalTest {
    private Journal testJournal;

    private static JournalEntry testEntry1;
    private static JournalEntry testEntry2;
    private static JournalEntry testEntry3;
    private static JournalEntry testEntry4;

    @BeforeAll
    static void runBeforeAll() {
        try {
            testEntry1 = new JournalEntry("01/01/2001","Test Title 1", 1, makeStatements());
            testEntry2 = new JournalEntry("02/29/2004", "Test Title 2",1, makeStatements());
            testEntry3 = new JournalEntry("12/31/2023","Test Title 3", 10, makeStatements());
            testEntry4 = new JournalEntry("12/31/2023","Test Title 4", 9, makeStatements());
        } catch (InvalidDateException e) {
            System.out.println("Date is out of bounds");
        } catch (InvalidScoreException e) {
            System.out.println("Score is not between 1-10");
        }
    }

    @BeforeEach
    void runBeforeEach() {
        testJournal = new Journal();
    }

    public static ArrayList<String> makeStatements() {
        ArrayList<String> gratitudeStatements = new ArrayList<>();
        gratitudeStatements.add("Test Statement 1");
        gratitudeStatements.add("Test Statement 2");
        gratitudeStatements.add("Test Statement 3");
        return gratitudeStatements;
    }

    @Test
    void testConstructor() {
        assertEquals(0, testJournal.journalSize());
    }

    @Test
    void testGetGoodDate() {
        try {
            testJournal.addJournalEntry(testEntry1);
            testJournal.addJournalEntry(testEntry2);
            testJournal.addJournalEntry(testEntry3);
        } catch (DuplicateDateException e) {
            fail("There should be no duplicate dates.");
        }
        try {
            assertEquals(testEntry1, testJournal.getEntriesDate("01/01/2001"));
            assertEquals(testEntry2, testJournal.getEntriesDate("02/29/2004"));
            assertEquals(testEntry3, testJournal.getEntriesDate("12/31/2023"));
        } catch (NoJournalEntryException e) {
            fail("No journal entry corresponds to the given date");
        }
    }

    @Test
    void testGetBadDate() {
        try {
            testJournal.addJournalEntry(testEntry1);
            testJournal.addJournalEntry(testEntry2);
            testJournal.addJournalEntry(testEntry3);
        } catch (DuplicateDateException e) {
            fail("There should be no duplicate dates.");
        }
        try {
            assertEquals(testEntry1, testJournal.getEntriesDate("14/72/35428"));
        } catch (NoJournalEntryException e) {
            assertEquals(3, testJournal.journalSize());
        }
    }

    @Test
    void testGetAllEntries() {
        ArrayList<JournalEntry> testEntries = new ArrayList<>();
        testEntries.add(testEntry1);
        testEntries.add(testEntry2);
        testEntries.add(testEntry3);
        try {
            testJournal.addJournalEntry(testEntry1);
            testJournal.addJournalEntry(testEntry2);
            testJournal.addJournalEntry(testEntry3);
        } catch (DuplicateDateException e) {
            fail("There should be no duplicate dates.");
        }
        assertEquals(testEntries, testJournal.getAllEntries());
    }

    @Test
    void testGetAllDates() {
        ArrayList<String> testDates = new ArrayList<>();
        testDates.add(testEntry1.getDate());
        testDates.add(testEntry2.getDate());
        testDates.add(testEntry3.getDate());
        try {
            testJournal.addJournalEntry(testEntry1);
            testJournal.addJournalEntry(testEntry2);
            testJournal.addJournalEntry(testEntry3);
        } catch (DuplicateDateException e) {
            fail("There should be no duplicate dates.");
        }
        assertEquals(testDates, testJournal.getAllDates());
    }

    @Test
    void testAddJournalEntry() {
        try {
            testJournal.addJournalEntry(testEntry1);
            assertEquals(1, testJournal.journalSize());
            testJournal.addJournalEntry(testEntry2);
            assertEquals(2, testJournal.journalSize());
            testJournal.addJournalEntry(testEntry3);
            assertEquals(3, testJournal.journalSize());
        } catch (DuplicateDateException e) {
            fail("There should be no duplicate dates.");
        }
    }

    @Test
    void testAddDuplicateDate() {
        try {
            testJournal.addJournalEntry(testEntry1);
            testJournal.addJournalEntry(testEntry2);
            testJournal.addJournalEntry(testEntry3);
            testJournal.addJournalEntry(testEntry4);
            fail("Expected a DuplicateDateException");
        } catch (DuplicateDateException e) {
            // pass
        }
    }

    @Test
    void deleteGoodDate() {
        try {
            testJournal.addJournalEntry(testEntry1);
            testJournal.addJournalEntry(testEntry2);
            testJournal.addJournalEntry(testEntry3);
        } catch (DuplicateDateException e) {
            fail("There should be no duplicate dates.");
        }
        assertEquals(3, testJournal.journalSize());
        testJournal.deleteJournalEntryDate("12/31/2023");
        assertEquals(2, testJournal.journalSize());
        testJournal.deleteJournalEntryDate("02/29/2004");
        assertEquals(1, testJournal.journalSize());
        try {
            assertEquals(testEntry1, testJournal.getEntriesDate("01/01/2001"));
        } catch (NoJournalEntryException e) {
            fail("No journal entry corresponds to the given date");
        }
    }

    @Test
    void deleteBadDate() {
        try {
            testJournal.addJournalEntry(testEntry1);
            testJournal.addJournalEntry(testEntry2);
            testJournal.addJournalEntry(testEntry3);
        } catch (DuplicateDateException e) {
            fail("There should be no duplicate dates.");
        }
        assertEquals(3, testJournal.journalSize());
        testJournal.deleteJournalEntryDate("12/30/2023");
        assertEquals(3, testJournal.journalSize());
        testJournal.deleteJournalEntryDate("02/29/2004");
        assertEquals(2, testJournal.journalSize());
        try {
            assertEquals(testEntry1, testJournal.getEntriesDate("01/01/2001"));
        } catch (NoJournalEntryException e) {
            assertEquals(2, testJournal.journalSize());
        }
    }

    @Test
    void sizeTest() {
        try {
            testJournal.addJournalEntry(testEntry1);
            assertEquals(1, testJournal.journalSize());
            testJournal.addJournalEntry(testEntry2);
            assertEquals(2, testJournal.journalSize());
            testJournal.addJournalEntry(testEntry3);
            assertEquals(3, testJournal.journalSize());
        } catch (DuplicateDateException e) {
            fail("There should be no duplicate dates.");
        }
    }
}
