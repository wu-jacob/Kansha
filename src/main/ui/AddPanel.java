package ui;

import exceptions.DuplicateDateException;
import exceptions.InvalidDateException;
import exceptions.InvalidScoreException;
import model.JournalEntry;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

// Page (panel) for adding new journal entries
public class AddPanel extends JPanel {
    private static final JLabel dateLabel = new JLabel("Enter date: ");
    private static final JLabel titleLabel = new JLabel("Enter title: ");
    private static final JLabel scoreLabel = new JLabel("Enter score: ");
    private static final JLabel statementLabel = new JLabel("Enter one thing you're grateful for today: ");
    private static final JLabel anotherStatementLabel = new JLabel("Enter another thing you're grateful for today");
    private static final JLabel invalidDateExceptionLabel = new JLabel(
            "Invalid Date! Please try again :D");
    private static final JLabel invalidScoreExceptionLabel = new JLabel(
            "Invalid Score! Please try again :D");
    private static final JLabel duplicateDateExceptionLabel = new JLabel(
            "A journal entry is already associated with this date! "
                    + "Please try again :D");


    private JTextField dateField;
    private JTextField titleField;
    private JTextField scoreField;
    private int count;
    private final List<String> statements;
    private JTextField statementField;
    private final JButton addAnotherStatementButton;
    private final JButton addButton;
    private final JButton homeButton;

    // EFFECTS: Constructs a new panel to display
    public AddPanel() {
        super();
        this.count = 0;
        this.statements = new ArrayList<>();
        this.addAnotherStatementButton = new JButton("Add another gratitude statement");
        this.addButton = new JButton("Add Entry");
        this.homeButton = new JButton("Home");

        setupPanel();
        setupListeners();
        setupLayout();
    }

    // MODIFIES: this
    // EFFECTS: Adds components to panel
    private void setupPanel() {
        this.dateField = new JTextField();
        this.titleField = new JTextField();
        this.scoreField = new JTextField();
        this.statementField = new JTextField();
        add(dateLabel);
        add(dateField);
        add(titleLabel);
        add(titleField);
        add(scoreLabel);
        add(scoreField);
        add(statementLabel);
        add(statementField);
        add(addAnotherStatementButton);
        add(addButton);
    }

    // MODIFIES: this
    // EFFECTS: Adds event listeners to buttons
    private void setupListeners() {
        addAnotherStatementButton.addActionListener(click -> {
            JTextField textField = new JTextField();
            textField.setName("statement" + count);
            count++;
            remove(addAnotherStatementButton);
            remove(addButton);
            add(anotherStatementLabel);
            add(textField);
            add(addButton);
            add(addAnotherStatementButton);
            revalidate();
        });
        addButton.addActionListener(click -> addFunction());
        homeButton.addActionListener(click -> leavePage());
    }

    // MODIFIES: this, JournalApp
    // EFFECTS: Adds the current journal entry to the journal
    private void addFunction() {
        String date = dateField.getText();
        String title = titleField.getText();
        String score = scoreField.getText();
        statements.add(statementField.getText());
        for (int i = 0; i < count; i++) {
            statements.add("statement" + i);
        }
        removeAll();
        AppState.add();
        try {
            JournalEntry returnEntry = new JournalEntry(date, title, parseInt(score), (ArrayList<String>) statements);
            JournalApp.getJournal().addJournalEntry(returnEntry);
            leavePage();
        } catch (InvalidDateException | StringIndexOutOfBoundsException e) {
            add(invalidDateExceptionLabel);
            add(homeButton);
        } catch (InvalidScoreException | NumberFormatException e) {
            add(invalidScoreExceptionLabel);
            add(homeButton);
        } catch (DuplicateDateException e) {
            add(duplicateDateExceptionLabel);
            add(homeButton);
        }
    }

    // MODIFIES: this
    // EFFECTS: takes user back to home page and refreshes current page
    private void leavePage() {
        removeAll();
        setupPanel();
        AppState.home();
    }

    // MODIFIES: this
    // EFFECTS: sets up panel layout
    private void setupLayout() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
}
