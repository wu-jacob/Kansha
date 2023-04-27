package ui;

import exceptions.NoJournalEntryException;
import model.JournalEntry;

import javax.swing.*;

// Page (panel) for searching for journal entries
public class SearchPanel extends JPanel {
    private static final JLabel noJournalEntryExceptionLabel = new JLabel(
            "No journal entry was found for the given date.");

    private final JLabel title;
    private final JTextField searchDate;
    private final JButton searchButton;
    private final JButton homeButton;

    // EFFECTS: Constructs a new search panel
    public SearchPanel() {
        super();
        this.title = new JLabel("Enter the date of the entry you wish to search for: ");
        this.searchDate = new JTextField();
        this.searchButton = new JButton("Search");
        this.homeButton = new JButton("Home");

        setupPanel();
        setupListeners();
        setupLayout();
    }

    // MODIFIES: this
    // EFFECTS: Adds all initial components to search panel
    private void setupPanel() {
        add(title);
        add(searchDate);
        add(searchButton);
    }

    // MODIFIES: this
    // EFFECTS: Adds event listeners to all buttons
    private void setupListeners() {
        searchButton.addActionListener(click -> {
            refresh();
            try {
                JournalEntry foundEntry = JournalApp.getJournal().getEntriesDate(searchDate.getText());
                JLabel dateLabel = new JLabel("Date: " + foundEntry.getDate());
                add(dateLabel);
                JLabel titleLabel = new JLabel("Title: " + foundEntry.getTitle());
                add(titleLabel);
                JLabel scoreLabel = new JLabel("Score: " + foundEntry.getScore());
                add(scoreLabel);
                for (String statement : foundEntry.getGratitudeStatements()) {
                    JLabel displayStatement = new JLabel("• " + statement);
                    add(displayStatement);
                }
            } catch (NoJournalEntryException e) {
                add(noJournalEntryExceptionLabel);
            } finally {
                add(homeButton);
                AppState.search();
            }
        });
        homeButton.addActionListener(click -> leavePage());
    }

    // MODIFIES: this
    // EFFECTS: Refreshes the search panel
    private void refresh() {
        removeAll();
        AppState.search();
    }

    // MODIFIES: this
    // EFFECTS: Refreshes the search panel and takes user back to the home page
    private void leavePage() {
        removeAll();
        setupPanel();
        setupListeners();
        setupLayout();
        revalidate();
        AppState.home();
    }

    // MODIFIES: this
    // EFFECTS: sets up the layout for the search panel
    private void setupLayout() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
}
