package persistence;

import model.JournalEntry;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Test for all classes related to reading/writing from/to JSON
public class JsonTest {
    protected void checkEntry(String date, String title, int score, ArrayList<String> statements, JournalEntry entry) {
        assertEquals(date, entry.getDate());
        assertEquals(title, entry.getTitle());
        assertEquals(score, entry.getScore());
        assertEquals(statements, entry.getGratitudeStatements());
    }

    protected ArrayList<String> makeStatements() {
        ArrayList<String> gratitudeStatements = new ArrayList<>();
        gratitudeStatements.add("Test Statement 1");
        gratitudeStatements.add("Test Statement 2");
        gratitudeStatements.add("Test Statement 3");
        return gratitudeStatements;
    }
}
