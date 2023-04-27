package ui;

// State of the application (determines next function to be called in JournalApp)
public class AppState {
    private static Boolean home;
    private static Boolean display;
    private static Boolean load;
    private static Boolean save;
    private static Boolean add;
    private static Boolean search;

    // EFFECTS: Constructs a new AppState
    public AppState() {
        home = false;
        display = false;
        load = false;
        save = false;
        add = false;
        search = false;
    }

    // MODIFIES: this
    // EFFECTS: creates a new journal app
    public void start() {
        JournalApp app = new JournalApp();
    }

    // MODIFIES: this
    // EFFECTS: changes the value of home to true
    public static void home() {
        home = true;
    }

    // MODIFIES: this
    // EFFECTS: changes the value of home to false
    public static void homeOff() {
        home = false;
    }

    // EFFECTS: returns the value of home
    public static Boolean getHome() {
        return home;
    }

    // MODIFIES: this
    // EFFECTS: changes the value of display to true
    public static void display() {
        display = true;
    }

    // MODIFIES: this
    // EFFECTS: changes the value of display to false
    public static void displayOff() {
        display = false;
    }

    // EFFECTS: returns the value of display
    public static Boolean getDisplay() {
        return display;
    }

    // MODIFIES: this
    // EFFECTS: changes the value of load to true
    public static void load() {
        load = true;
    }

    // MODIFIES: this
    // EFFECTS: changes the value of load to false
    public static void loadOff() {
        load = false;
    }

    // EFFECTS: returns the value of load
    public static Boolean getLoad() {
        return load;
    }

    // MODIFIES: this
    // EFFECTS: changes the value of save to true
    public static void save() {
        save = true;
    }

    // MODIFIES: this
    // EFFECTS: changes the value of save to false
    public static void saveOff() {
        save = false;
    }

    // EFFECTS: returns the value of save
    public static Boolean getSave() {
        return save;
    }

    // MODIFIES: this
    // EFFECTS: changes the value of add to true
    public static void add() {
        add = true;
    }

    // MODIFIES: this
    // EFFECTS: changes the value of add to false
    public static void addOff() {
        add = false;
    }

    // EFFECTS: returns the value of add
    public static Boolean getAdd() {
        return add;
    }

    // MODIFIES: this
    // EFFECTS: changes the value of search to true
    public static void search() {
        search = true;
    }

    // MODIFIES: this
    // EFFECTS: changes the value of search to false
    public static void searchOff() {
        search = false;
    }

    // EFFECTS: returns the value of search
    public static Boolean getSearch() {
        return search;
    }

    // EFFECTS: always returns false to ensure application is running
    // this function may modify a boolean value in the future
    public static Boolean quit() {
        return false;
    }
}
