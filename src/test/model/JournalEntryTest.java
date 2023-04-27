package model;

import exceptions.InvalidDateException;
import exceptions.InvalidScoreException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// Test for the JournalEntry class
class JournalEntryTest {

    public ArrayList<String> makeStatements() {
        ArrayList<String> gratitudeStatements = new ArrayList<>();
        gratitudeStatements.add("Test Statement 1");
        gratitudeStatements.add("Test Statement 2");
        gratitudeStatements.add("Test Statement 3");
        return gratitudeStatements;
    }

    @Test
    void testConstructorNormal() {
        JournalEntry testEntry = null;
        try {
            testEntry = new JournalEntry("01/01/2001", "Test Title", 5, makeStatements());
        } catch (InvalidDateException e) {
            fail("Date is out of bounds");
        } catch (InvalidScoreException e) {
            fail("Score is out of bounds");
        }
        assertEquals("01/01/2001", testEntry.getDate());
        assertEquals("Test Title", testEntry.getTitle());
        assertEquals(5, testEntry.getScore());
        assertEquals(makeStatements(), testEntry.getGratitudeStatements());
    }

    @Test
    void testConstructorBadScore() {
        try {
            JournalEntry testEntry = new JournalEntry("01/01/2001", "Test Title", 11, makeStatements());
            fail();
        } catch (InvalidDateException e) {
            fail("Date is out of bounds");
        } catch (InvalidScoreException ignored) {}
        try {
            JournalEntry testEntry = new JournalEntry("01/01/2001", "Test Title", 0, makeStatements());
            fail();
        } catch (InvalidDateException e) {
            fail("Date is out of bounds");
        } catch (InvalidScoreException ignored) {}
    }
}