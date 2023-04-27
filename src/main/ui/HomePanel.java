package ui;

import javax.swing.*;

// Home page (panel)
public class HomePanel extends JPanel {

    private final JLabel title;
    private final JButton displayButton;
    private final JButton loadButton;
    private final JButton saveButton;
    private final JButton addButton;
    private final JButton searchButton;

    // EFFECTS: Constructs a new home panel
    public HomePanel() {
        super();
        ImageIcon image = new ImageIcon("data/title.png");
        this.title = new JLabel(image);
        this.displayButton = new JButton("Display Journal Entries");
        this.loadButton = new JButton("Load");
        this.saveButton = new JButton("Save");
        this.addButton = new JButton("Add");
        this.searchButton = new JButton("Search");

        setupPanel();
        setupListeners();
        setupLayout();
    }

    // MODIFIES: this
    // EFFECTS: Adds all initial components to home panel
    private void setupPanel() {
        this.add(title);
        this.add(displayButton);
        this.add(loadButton);
        this.add(saveButton);
        this.add(addButton);
        this.add(searchButton);
    }

    // MODIFIES: this
    // EFFECTS: Adds event listeners to all buttons
    private void setupListeners() {
        displayButton.addActionListener(click -> AppState.display());
        loadButton.addActionListener(click -> AppState.load());
        saveButton.addActionListener(click -> AppState.save());
        addButton.addActionListener(click -> AppState.add());
        searchButton.addActionListener(click -> AppState.search());
    }

    // MODIFIES: this
    // EFFECTS: sets up the layout for the home panel
    private void setupLayout() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
}
