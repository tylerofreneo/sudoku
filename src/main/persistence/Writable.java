package persistence;

import org.json.JSONObject;

// from: github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Interface for something writable to json object
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}

