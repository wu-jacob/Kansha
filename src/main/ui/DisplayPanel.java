package ui;

import model.JournalEntry;

import javax.swing.*;

// Page (panel) for displaying all journal entries
public class DisplayPanel extends JPanel {

    private final JButton homeButton;

    // EFFECTS: Constructs a new display panel
    public DisplayPanel() {
        super();
        this.homeButton = new JButton("Home");

        setupPanel();
        setupListeners();
        setupLayout();
    }

    // MODIFIES: this
    // EFFECTS: Adds all initial components to display panel
    private void setupPanel() {
        for (JournalEntry entry : JournalApp.getJournal().getAllEntries()) {
            this.add(new JLabel("Date Written: " + entry.getDate()
                    + " - Title: " + entry.getTitle()));
        }
        add(homeButton);
    }

    // MODIFIES: this
    // EFFECTS: Adds event listener to home button
    private void setupListeners() {
        homeButton.addActionListener(click -> {
            removeAll();
            AppState.home();
        });
    }

    // MODIFIES: this
    // EFFECTS: sets up the layout for the display panel
    private void setupLayout() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
}
