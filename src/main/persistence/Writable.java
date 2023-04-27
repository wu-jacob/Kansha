package persistence;

import org.json.*;

// Interface for all classes whose objects can be converted into JSON objects.
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
