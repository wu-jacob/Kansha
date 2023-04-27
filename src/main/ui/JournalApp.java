package ui;

import exceptions.DuplicateDateException;
import exceptions.InvalidDateException;
import exceptions.InvalidScoreException;
import exceptions.NoJournalEntryException;
import model.*;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.JFrame;

// Gratitude Journal Application
public class JournalApp extends JFrame implements WindowListener {
    private Boolean running;
    private final HomePanel homePanel;
    private DisplayPanel displayPanel;
    private final AddPanel addPanel;
    private final SearchPanel searchPanel;
    private static final String JSON_STORE = "./data/journal.json";
    private final JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    private final JsonReader jsonReader = new JsonReader(JSON_STORE);
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;
    private Scanner input;
    private static Journal journal = new Journal();

    // MODIFIES: this
    // EFFECTS: runs the journal app
    public JournalApp() {
        super();
        this.running = true;
        this.homePanel = new HomePanel();
        this.displayPanel = new DisplayPanel();
        this.addPanel = new AddPanel();
        this.searchPanel = new SearchPanel();
        setupFrame();
        runJournal();
    }

    // MODIFIES: this
    // EFFECTS:  draws the JFrame window where this JournalApp will operate
    private void setupFrame() {
        this.setContentPane(homePanel);
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(true);
        this.setTitle("Gratitude Journal");
        this.setDefaultCloseOperation(JournalApp.EXIT_ON_CLOSE);
        this.addWindowListener(this);
        this.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: continuously calls eventHandler while app is running
    private void runJournal() {

        init();

        while (running) {
            eventHandler();
        }
    }

    // MODIFIES: this
    // EFFECTS: instantiates a new scanner to take in user input
    private void init() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: prints the main menu onto the console
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tadd -> add journal entry");
        System.out.println("\tsearch -> search for journal entry");
        System.out.println("\tdisplay -> display all journal entries");
        System.out.println("\tdelete -> delete a journal entry");
        System.out.println("\tload -> load journal");
        System.out.println("\tsave -> save journal");
        System.out.println("\tquit -> quit");
    }

    // EFFECTS: processes the command of the user
    private void eventHandler() {
        if (AppState.getHome()) {
            displayHome();
        } else if (AppState.getDisplay()) {
            displayDisplay();
        } else if (AppState.getLoad()) {
            loadJournal();
        } else if (AppState.getSave()) {
            saveJournal();
        } else if (AppState.getAdd()) {
            this.setContentPane(addPanel);
            AppState.addOff();
            this.setVisible(true);
        } else if (AppState.getSearch()) {
            this.setContentPane(searchPanel);
            AppState.searchOff();
            this.setVisible(true);
        } else if (AppState.quit()) {
            running = false;
        } else {
            this.setVisible(true);
        }
    }

    // MODIFIES: this
    // EFFECTS: processes the command of the user
    private void processCommand(String command) {
        if (Objects.equals(command, "add")) {
            writeEntry();
        } else if (Objects.equals(command, "search")) {
            searchEntry();
        } else if (Objects.equals(command, "display")) {
            displayAllEntries();
        } else if (Objects.equals(command, "delete")) {
            removeEntry();
        } else if (Objects.equals(command, "load")) {
            loadJournal();
        } else if (Objects.equals(command, "save")) {
            saveJournal();
        } else {
            System.out.println("\nSelection not valid. Please pick a valid command.");
        }
    }

    // EFFECTS: sends the user back to the home page
    private void displayHome() {
        this.setContentPane(homePanel);
        AppState.homeOff();
        this.setVisible(true);
    }

    // EFFECTS: sends the user to the display page
    private void displayDisplay() {
        displayPanel = new DisplayPanel();
        this.setContentPane(displayPanel);
        AppState.displayOff();
        this.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: takes in user input and makes a new journal entry
    private void writeEntry() {
        try {
            System.out.println("\nEnter today's date (MM/DD/YYYY): ");
            String date = input.next();
            System.out.println("\nEnter a title for today's entry: ");
            String title = input.next();
            System.out.println("\nOn a scale of 1-10, how was your day?");
            int score = input.nextInt();
            addEntry(date, title, score, writeStatements());
        } catch (InputMismatchException | InvalidScoreException e) {
            System.out.println("\nInvalid score! Please enter a score between 1-10!");
            writeEntry();
        }
    }

    // EFFECTS: takes in user input and returns a list of gratitude statements
    private ArrayList<String> writeStatements() {
        ArrayList<String> statements = new ArrayList<>();
        System.out.println("\nEnter one thing that you're grateful for today:");
        String gratitudeStatement = input.next();
        statements.add(gratitudeStatement);
        while (!gratitudeStatement.equalsIgnoreCase("done")) {
            System.out.println("\nEnter another thing you're grateful for today: (type \"done\" to exit)");
            gratitudeStatement = input.next();
            if (Objects.equals(gratitudeStatement, "done")) {
                break;
            }
            statements.add(gratitudeStatement);
        }
        return statements;
    }

    // MODIFIES: this
    // EFFECTS: creates a new journal entry and adds it into the journal
    private void addEntry(String date, String title, int score, ArrayList<String> statements)
            throws InvalidScoreException {
        try {
            JournalEntry newEntry = new JournalEntry(date, title, score, statements);
            journal.addJournalEntry(newEntry);
            System.out.println("Journal Entry Added!");
        } catch (InvalidDateException e) {
            System.out.println("Invalid Date! Please enter a valid date: ");
            writeEntry();
        } catch (DuplicateDateException e) {
            System.out.println("A journal entry is already associated with this date.");
            writeEntry();
        }
    }

    // EFFECTS: takes in a date and displays the journal entry corresponding to
    // that date if it exists
    private void searchEntry() {
        System.out.println("\nEnter the date of the journal entry to search for: (MM/DD/YYYY)");
        String date = input.next();
        try {
            JournalEntry foundEntry = journal.getEntriesDate(date);
            System.out.println("\nDate Written: " + foundEntry.getDate());
            System.out.println("\nTitle: " + foundEntry.getTitle());
            System.out.println("\nScore: " + foundEntry.getScore());
            System.out.println("\nGratitude Statements: ");
            for (String statement : foundEntry.getGratitudeStatements()) {
                System.out.println("\n• " + statement);
            }
        } catch (NoJournalEntryException e) {
            System.out.println("\nNo entry was written on this date!");
            displayMenu();
        }
    }

    // EFFECTS: displays the dates and titles of all journal
    // entries in the journal
    public void displayAllEntries() {
        for (JournalEntry entry : journal.getAllEntries()) {
            System.out.println("\nDate Written: " + entry.getDate()
                    + " - Title: " + entry.getTitle());
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes the entry in the journal that corresponds to the date given by
    // user input
    private void removeEntry() {
        System.out.println("\nEnter the date of the journal entry to delete: (MM/DD/YYYY)");
        String date = input.next();
        try {
            JournalEntry foundEntry = journal.getEntriesDate(date);
            journal.deleteJournalEntryDate(foundEntry.getDate());
        } catch (NoJournalEntryException e) {
            System.out.println("\nNo entry was written on this date!");
            displayMenu();
        }
        System.out.println("Journal entry deleted.");
    }

    // MODIFIES: this
    // EFFECTS: loads journal from file
    private void loadJournal() {
        AppState.loadOff();
        try {
            journal = jsonReader.read();
            System.out.println("Loaded journal from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: saves the workroom to file
    private void saveJournal() {
        AppState.saveOff();
        try {
            jsonWriter.open();
            jsonWriter.write(journal);
            jsonWriter.close();
            System.out.println("Saved journal to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: returns this journal
    public static Journal getJournal() {
        return journal;
    }

    @Override
    public void windowOpened(WindowEvent e) {
        //pass
    }

    @Override
    public void windowClosing(WindowEvent e) {
        for (Event event : EventLog.getInstance()) {
            System.out.println(event.toString());
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {
        //pass
    }

    @Override
    public void windowIconified(WindowEvent e) {
        //pass
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        //pass
    }

    @Override
    public void windowActivated(WindowEvent e) {
        //pass
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        //pass
    }
}
